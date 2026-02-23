package com.crowdsourced.wasteplatform.service.collector;

import com.crowdsourced.wasteplatform.dto.common.PageResponse;
import com.crowdsourced.wasteplatform.dto.collector.request.UpdateCollectorStatusRequest;
import com.crowdsourced.wasteplatform.dto.collector.request.UploadProofRequest;
import com.crowdsourced.wasteplatform.dto.collector.response.AssignmentResponse;
import com.crowdsourced.wasteplatform.entity.CollectorStatus;
import com.crowdsourced.wasteplatform.entity.MediaType;
import com.crowdsourced.wasteplatform.entity.ReportAssignment;
import com.crowdsourced.wasteplatform.entity.ReportMedia;
import com.crowdsourced.wasteplatform.entity.ReportStatus;
import com.crowdsourced.wasteplatform.entity.ReportStatusHistory;
import com.crowdsourced.wasteplatform.entity.WasteReport;
import com.crowdsourced.wasteplatform.exception.AppException;
import com.crowdsourced.wasteplatform.exception.ErrorCode;
import com.crowdsourced.wasteplatform.repository.ReportAssignmentRepository;
import com.crowdsourced.wasteplatform.repository.ReportMediaRepository;
import com.crowdsourced.wasteplatform.repository.ReportStatusHistoryRepository;
import com.crowdsourced.wasteplatform.repository.WasteReportRepository;
import com.crowdsourced.wasteplatform.service.reward.PointAwardService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CollectorAssignmentService {

    private final ReportAssignmentRepository assignmentRepository;
    private final WasteReportRepository reportRepository;
    private final ReportStatusHistoryRepository historyRepository;
    private final ReportMediaRepository mediaRepository;
    private final PointAwardService pointAwardService;

    @Transactional(readOnly = true)
    public PageResponse<AssignmentResponse> getMyAssignments(String collectorIdStr, String statusOptional, Pageable pageable) {
        UUID collectorId = parseUuid(collectorIdStr, "collectorId");
        Page<ReportAssignment> page;
        if (statusOptional != null && !statusOptional.isBlank()) {
            CollectorStatus status = CollectorStatus.valueOf(statusOptional);
            page = assignmentRepository.findAllByCollectorIdAndCollectorStatusOrderByAssignedAtDesc(collectorId, status, pageable);
        } else {
            page = assignmentRepository.findAllByCollectorIdOrderByAssignedAtDesc(collectorId, pageable);
        }
        Page<AssignmentResponse> mapped = page.map(this::toResponse);
        return PageResponse.from(mapped);
    }

    @Transactional
    public AssignmentResponse updateStatus(String assignmentIdStr, String collectorIdStr, UpdateCollectorStatusRequest req) {
        ReportAssignment assignment = loadOwnedAssignment(assignmentIdStr, collectorIdStr);
        CollectorStatus from = assignment.getCollectorStatus();
        CollectorStatus to = req.getStatus();

        validateTransition(from, to);

        if (req.getLastKnownLatitude() != null) {
            assignment.setLastKnownLatitude(req.getLastKnownLatitude());
        }
        if (req.getLastKnownLongitude() != null) {
            assignment.setLastKnownLongitude(req.getLastKnownLongitude());
        }
        assignment.setCollectorStatus(to);
        assignmentRepository.save(assignment);

        WasteReport report = loadReport(assignment.getReportId());
        ReportStatus reportFrom = report.getCurrentStatus();
        ReportStatus reportTo = mapCollectorToReportStatus(to, reportFrom);
        if (reportTo != null && reportTo != reportFrom) {
            report.setCurrentStatus(reportTo);
            reportRepository.save(report);
            historyRepository.save(ReportStatusHistory.builder()
                .reportId(report.getId())
                .fromStatus(reportFrom)
                .toStatus(reportTo)
                .note(req.getNote())
                .changedBy(parseUuid(collectorIdStr, "collectorId"))
                .build());

            // MVP rule: award points exactly when report becomes COLLECTED.
            if (reportTo == ReportStatus.COLLECTED) {
                pointAwardService.awardPointsForReport(report.getId().toString(), collectorIdStr);
            }
        }

        return toResponse(assignment);
    }

    @Transactional
    public AssignmentResponse uploadProof(String assignmentIdStr, String collectorIdStr, UploadProofRequest req) {
        ReportAssignment assignment = loadOwnedAssignment(assignmentIdStr, collectorIdStr);
        if (assignment.getCollectorStatus() != CollectorStatus.COLLECTED) {
            throw new AppException(ErrorCode.INVALID_REPORT_STATUS, "Chỉ upload proof khi trạng thái COLLECTED");
        }
        UUID collectorId = parseUuid(collectorIdStr, "collectorId");
        for (String url : req.getProofUrls()) {
            mediaRepository.save(ReportMedia.builder()
                .reportId(assignment.getReportId())
                .mediaType(MediaType.COLLECTED_PROOF)
                .url(url)
                .createdBy(collectorId)
                .takenAt(req.getTakenAt())
                .build());
        }
        return toResponse(assignment);
    }

    private AssignmentResponse toResponse(ReportAssignment assignment) {
        WasteReport report = loadReport(assignment.getReportId());
        return AssignmentResponse.builder()
            .assignmentId(assignment.getId())
            .reportId(assignment.getReportId())
            .collectorStatus(assignment.getCollectorStatus())
            .assignedAt(assignment.getAssignedAt())
            .areaId(report.getAreaId())
            .wasteCategoryId(report.getWasteCategoryId())
            .addressText(report.getAddressText())
            .latitude(report.getLatitude())
            .longitude(report.getLongitude())
            .currentStatus(report.getCurrentStatus())
            .build();
    }

    private void validateTransition(CollectorStatus from, CollectorStatus to) {
        if (from == CollectorStatus.ASSIGNED) {
            if (to == CollectorStatus.ON_THE_WAY || to == CollectorStatus.COLLECTED || to == CollectorStatus.FAILED) return;
        }
        if (from == CollectorStatus.ON_THE_WAY) {
            if (to == CollectorStatus.COLLECTED || to == CollectorStatus.FAILED) return;
        }
        throw new AppException(ErrorCode.INVALID_REPORT_STATUS, "Không hợp lệ chuyển trạng thái từ " + from + " sang " + to);
    }

    private ReportStatus mapCollectorToReportStatus(CollectorStatus collectorStatus, ReportStatus current) {
        return switch (collectorStatus) {
            case ASSIGNED -> ReportStatus.ASSIGNED;
            case ON_THE_WAY -> ReportStatus.ON_THE_WAY;
            case COLLECTED -> ReportStatus.COLLECTED;
            case FAILED -> ReportStatus.ACCEPTED; // quay lại trạng thái chờ reassignment
        };
    }

    private ReportAssignment loadOwnedAssignment(String assignmentIdStr, String collectorIdStr) {
        UUID assignmentId = parseUuid(assignmentIdStr, "assignmentId");
        UUID collectorId = parseUuid(collectorIdStr, "collectorId");
        return assignmentRepository.findByIdAndCollectorId(assignmentId, collectorId)
            .orElseThrow(() -> new AppException(ErrorCode.REPORT_ACCESS_DENIED, "Assignment not found or not owned by collector"));
    }

    private WasteReport loadReport(UUID reportId) {
        return reportRepository.findById(reportId)
            .orElseThrow(() -> new AppException(ErrorCode.REPORT_NOT_FOUND, "Report not found"));
    }

    private UUID parseUuid(String value, String field) {
        try {
            return UUID.fromString(value);
        } catch (Exception ex) {
            throw new AppException(ErrorCode.BAD_REQUEST, "Invalid UUID for " + field);
        }
    }
}
