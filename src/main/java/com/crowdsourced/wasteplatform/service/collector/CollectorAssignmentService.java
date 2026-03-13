package com.crowdsourced.wasteplatform.service.collector;

import com.crowdsourced.wasteplatform.dto.collector.request.UpdateCollectorStatusRequest;
import com.crowdsourced.wasteplatform.dto.collector.request.UploadProofRequest;
import com.crowdsourced.wasteplatform.dto.collector.response.AssignmentResponse;
import com.crowdsourced.wasteplatform.dto.common.PageResponse;
import com.crowdsourced.wasteplatform.dto.report.response.ReportMediaResponse;
import com.crowdsourced.wasteplatform.dto.report.response.ReportStatusHistoryResponse;
import com.crowdsourced.wasteplatform.dto.report.response.WasteReportResponse;
import com.crowdsourced.wasteplatform.entity.CollectorStatus;
import com.crowdsourced.wasteplatform.entity.MediaType;
import com.crowdsourced.wasteplatform.entity.ReportAssignment;
import com.crowdsourced.wasteplatform.entity.ReportMedia;
import com.crowdsourced.wasteplatform.entity.ReportStatus;
import com.crowdsourced.wasteplatform.entity.ReportStatusHistory;
import com.crowdsourced.wasteplatform.entity.WasteReport;
import com.crowdsourced.wasteplatform.exception.AppException;
import com.crowdsourced.wasteplatform.exception.ErrorCode;
import com.crowdsourced.wasteplatform.mapper.ReportMediaMapper;
import com.crowdsourced.wasteplatform.mapper.ReportStatusHistoryMapper;
import com.crowdsourced.wasteplatform.mapper.WasteReportMapper;
import com.crowdsourced.wasteplatform.repository.ReportAssignmentRepository;
import com.crowdsourced.wasteplatform.repository.ReportMediaRepository;
import com.crowdsourced.wasteplatform.repository.ReportStatusHistoryRepository;
import com.crowdsourced.wasteplatform.repository.WasteReportRepository;
import com.crowdsourced.wasteplatform.service.email.EmailService;
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
    private final ReportMediaMapper mediaMapper;
    private final ReportStatusHistoryMapper historyMapper;
    private final WasteReportMapper reportMapper;
    private final EmailService emailService;

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
        return PageResponse.from(page.map(this::toResponse));
    }

    @Transactional(readOnly = true)
    public WasteReportResponse getOwnedReportDetail(String reportIdStr, String collectorIdStr) {
        UUID reportId = parseUuid(reportIdStr, "reportId");
        UUID collectorId = parseUuid(collectorIdStr, "collectorId");
        assignmentRepository.findByReportIdAndCollectorId(reportId, collectorId)
            .orElseThrow(() -> new AppException(ErrorCode.REPORT_ACCESS_DENIED, "Report not assigned to collector"));
        return enrichReport(loadReport(reportId));
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
        ReportStatus reportTo = mapCollectorToReportStatus(to);
        if (reportTo != reportFrom) {
            report.setCurrentStatus(reportTo);
            reportRepository.save(report);
            historyRepository.save(ReportStatusHistory.builder()
                .reportId(report.getId())
                .fromStatus(reportFrom)
                .toStatus(reportTo)
                .note(req.getNote())
                .changedBy(parseUuid(collectorIdStr, "collectorId"))
                .build());

            if (reportTo == ReportStatus.COLLECTED) {
                pointAwardService.awardPointsForReport(report.getId().toString(), collectorIdStr);

                String subject = "Your Waste Report Has Been Accepted";

                StringBuilder mediaSection = new StringBuilder();

if (report.getMediaList() != null && !report.getMediaList().isEmpty()) {
    mediaSection.append("- Media Attachments:\n");
    for (ReportMedia media : report.getMediaList()) {
        mediaSection.append("  + ").append(media.getUrl()).append("\n");
    }
} else {
    mediaSection.append("- Media Attachments: None\n");
}
        String content =
        "Dear User,\n\n" +

        "We are pleased to inform you that your submitted waste report has been reviewed and officially accepted by our management team.\n\n" +

        "Our operational team will proceed with the necessary actions to address the reported issue as soon as possible.\n\n" +

        "Report Information:\n" +
        "- Area: " + report.getArea().getName() + "\n" +
        "- Waste Category: " + report.getWasteCategory().getName() + "\n" +
        "- Status: " + report.getCurrentStatus() + "\n" +
        "- Estimated Weight (kg): " + report.getEstimatedWeightKg() + "\n" +
        "- Actual Weight (kg): " + report.getActualWeightKg() + "\n" +
        "- Location Coordinates: (" + report.getLatitude() + ", " + report.getLongitude() + ")\n" +
        "- Address: " + report.getAddressText() + "\n" +
        "- Description: " + report.getDescription() + "\n" +
        "- Created At: " + report.getCreatedAt() + "\n" +
        "- Last Updated: " + report.getUpdatedAt() + "\n" +
        mediaSection.toString() + "\n" +

        "We sincerely appreciate your proactive contribution to maintaining environmental cleanliness and community well-being.\n\n" +

        "You will receive further updates once the issue has been resolved.\n\n" +

        "Best regards,\n" +
        "Waste Management Support Team\n" +
        "Crowdsourced Waste Platform";

        emailService.sendComplaintResolvedEmail(
            report.getCitizen().getEmail(),
            subject,
            content
        );
            }
        }

        return toResponse(assignment);
    }

    @Transactional
    public AssignmentResponse uploadProof(String assignmentIdStr, String collectorIdStr, UploadProofRequest req) {
        ReportAssignment assignment = loadOwnedAssignment(assignmentIdStr, collectorIdStr);
        if (assignment.getCollectorStatus() != CollectorStatus.COLLECTED) {
            throw new AppException(ErrorCode.INVALID_REPORT_STATUS, "Only COLLECTED assignment can upload proof");
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

    private WasteReportResponse enrichReport(WasteReport report) {
        List<ReportMediaResponse> media = mediaRepository.findByReportIdOrderByCreatedAtAsc(report.getId())
            .stream()
            .map(mediaMapper::toResponse)
            .toList();
        List<ReportStatusHistoryResponse> history = historyRepository.findByReportIdOrderByCreatedAtAsc(report.getId())
            .stream()
            .map(historyMapper::toResponse)
            .toList();
        return reportMapper.toResponse(report, media, history);
    }

    private void validateTransition(CollectorStatus from, CollectorStatus to) {
        if (from == CollectorStatus.ASSIGNED && (to == CollectorStatus.ON_THE_WAY || to == CollectorStatus.COLLECTED || to == CollectorStatus.FAILED)) {
            return;
        }
        if (from == CollectorStatus.ON_THE_WAY && (to == CollectorStatus.COLLECTED || to == CollectorStatus.FAILED)) {
            return;
        }
        throw new AppException(ErrorCode.INVALID_REPORT_STATUS, "Invalid collector status transition from " + from + " to " + to);
    }

    private ReportStatus mapCollectorToReportStatus(CollectorStatus collectorStatus) {
        return switch (collectorStatus) {
            case ASSIGNED -> ReportStatus.ASSIGNED;
            case ON_THE_WAY -> ReportStatus.ON_THE_WAY;
            case COLLECTED -> ReportStatus.COLLECTED;
            case FAILED -> ReportStatus.ACCEPTED;
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
