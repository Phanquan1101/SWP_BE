package com.crowdsourced.wasteplatform.service.enterprise;

import com.crowdsourced.wasteplatform.dto.common.PageResponse;
import com.crowdsourced.wasteplatform.dto.enterprise.response.InboxReportItemResponse;
import com.crowdsourced.wasteplatform.dto.report.response.ReportMediaResponse;
import com.crowdsourced.wasteplatform.dto.report.response.ReportStatusHistoryResponse;
import com.crowdsourced.wasteplatform.dto.report.response.WasteReportResponse;
import com.crowdsourced.wasteplatform.entity.CollectorStatus;
import com.crowdsourced.wasteplatform.entity.ReportAssignment;
import com.crowdsourced.wasteplatform.entity.ReportStatus;
import com.crowdsourced.wasteplatform.entity.ReportStatusHistory;
import com.crowdsourced.wasteplatform.entity.User;
import com.crowdsourced.wasteplatform.entity.WasteReport;
import com.crowdsourced.wasteplatform.exception.AppException;
import com.crowdsourced.wasteplatform.exception.ErrorCode;
import com.crowdsourced.wasteplatform.mapper.EnterpriseDispatchMapper;
import com.crowdsourced.wasteplatform.mapper.ReportMediaMapper;
import com.crowdsourced.wasteplatform.mapper.ReportStatusHistoryMapper;
import com.crowdsourced.wasteplatform.mapper.WasteReportMapper;
import com.crowdsourced.wasteplatform.repository.ReportAssignmentRepository;
import com.crowdsourced.wasteplatform.repository.ReportMediaRepository;
import com.crowdsourced.wasteplatform.repository.ReportStatusHistoryRepository;
import com.crowdsourced.wasteplatform.repository.UserRepository;
import com.crowdsourced.wasteplatform.repository.UserRoleRepository;
import com.crowdsourced.wasteplatform.repository.WasteCapabilityRepository;
import com.crowdsourced.wasteplatform.repository.WasteReportRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DispatchService {

    private final WasteReportRepository reportRepository;
    private final WasteCapabilityRepository capabilityRepository;
    private final ReportAssignmentRepository assignmentRepository;
    private final ReportStatusHistoryRepository historyRepository;
    private final ReportMediaRepository mediaRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final EnterpriseDispatchMapper dispatchMapper;
    private final ReportMediaMapper mediaMapper;
    private final ReportStatusHistoryMapper historyMapper;
    private final WasteReportMapper reportMapper;

    @Transactional(readOnly = true)
    public PageResponse<InboxReportItemResponse> getInbox(String areaIdOptional, String statusOptional, Pageable pageable) {
        UUID areaId = parseUuidNullable(areaIdOptional, "areaId");
        ReportStatus status = parseStatusOrDefault(statusOptional, ReportStatus.PENDING);
        Page<WasteReport> page = reportRepository.findInbox(areaId, status, pageable);
        Page<InboxReportItemResponse> mapped = page.map(dispatchMapper::toInboxItem);
        return PageResponse.from(mapped);
    }

    @Transactional(readOnly = true)
    public PageResponse<InboxReportItemResponse> getAllReports(Pageable pageable) {
        Page<InboxReportItemResponse> mapped = reportRepository.findAll(pageable)
            .map(dispatchMapper::toInboxItem);
        return PageResponse.from(mapped);
    }

    @Transactional
    public WasteReportResponse acceptReport(String reportId, String managerId) {
        UUID managerUuid = parseUuid(managerId, "managerId");
        WasteReport report = loadReport(reportId);
        if (report.getCurrentStatus() != ReportStatus.PENDING) {
            throw new AppException(ErrorCode.INVALID_REPORT_STATUS, "Only PENDING report can be accepted");
        }
        if (!capabilityRepository.existsByWasteCategoryIdAndAcceptingTrue(report.getWasteCategoryId())) {
            throw new AppException(ErrorCode.CAPABILITY_NOT_ACCEPTING, "Waste category capability is not accepting");
        }

        ReportStatus from = report.getCurrentStatus();
        report.setCurrentStatus(ReportStatus.ACCEPTED);
        reportRepository.save(report);
        historyRepository.save(ReportStatusHistory.builder()
            .reportId(report.getId())
            .fromStatus(from)
            .toStatus(ReportStatus.ACCEPTED)
            .changedBy(managerUuid)
            .note("Accepted by enterprise manager")
            .build());

        return enrichReport(report);
    }

    @Transactional
    public WasteReportResponse rejectReport(String reportId, String managerId, String reason) {
        UUID managerUuid = parseUuid(managerId, "managerId");
        WasteReport report = loadReport(reportId);
        if (report.getCurrentStatus() != ReportStatus.PENDING) {
            throw new AppException(ErrorCode.INVALID_REPORT_STATUS, "Only PENDING report can be rejected");
        }

        ReportStatus from = report.getCurrentStatus();
        report.setCurrentStatus(ReportStatus.REJECTED);
        reportRepository.save(report);
        historyRepository.save(ReportStatusHistory.builder()
            .reportId(report.getId())
            .fromStatus(from)
            .toStatus(ReportStatus.REJECTED)
            .changedBy(managerUuid)
            .note(reason)
            .build());

        return enrichReport(report);
    }

    @Transactional
    public WasteReportResponse assignCollector(String reportId, String managerId, String collectorId) {
        UUID managerUuid = parseUuid(managerId, "managerId");
        UUID collectorUuid = parseUuid(collectorId, "collectorId");
        WasteReport report = loadReport(reportId);

        if (report.getCurrentStatus() != ReportStatus.ACCEPTED) {
            throw new AppException(ErrorCode.INVALID_REPORT_STATUS, "Only ACCEPTED report can be assigned");
        }

        User collector = userRepository.findById(collectorUuid)
            .orElseThrow(() -> new AppException(ErrorCode.COLLECTOR_NOT_FOUND, "Collector not found"));
        if (!userRoleRepository.existsByUserIdAndRoleCode(collectorUuid, "ROLE_COLLECTOR")) {
            throw new AppException(ErrorCode.COLLECTOR_NOT_FOUND, "Collector role not found");
        }
        if (collector.getAreaId() == null) {
            throw new AppException(ErrorCode.COLLECTOR_AREA_REQUIRED, "Collector must have area");
        }
        if (!collector.getAreaId().equals(report.getAreaId())) {
            throw new AppException(ErrorCode.COLLECTOR_AREA_MISMATCH, "Collector area does not match report area");
        }
        if (assignmentRepository.existsByReportId(report.getId())) {
            throw new AppException(ErrorCode.ASSIGNMENT_ALREADY_EXISTS, "Assignment already exists for report");
        }

        assignmentRepository.save(ReportAssignment.builder()
            .reportId(report.getId())
            .collectorId(collectorUuid)
            .assignedBy(managerUuid)
            .collectorStatus(CollectorStatus.ASSIGNED)
            .build());

        ReportStatus from = report.getCurrentStatus();
        report.setCurrentStatus(ReportStatus.ASSIGNED);
        reportRepository.save(report);
        historyRepository.save(ReportStatusHistory.builder()
            .reportId(report.getId())
            .fromStatus(from)
            .toStatus(ReportStatus.ASSIGNED)
            .changedBy(managerUuid)
            .note("Assigned to collector " + collectorUuid)
            .build());

        return enrichReport(report);
    }

    private WasteReport loadReport(String reportId) {
        UUID reportUuid = parseUuid(reportId, "reportId");
        return reportRepository.findById(reportUuid)
            .orElseThrow(() -> new AppException(ErrorCode.REPORT_NOT_FOUND, "Report not found"));
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

    private ReportStatus parseStatusOrDefault(String value, ReportStatus defaultValue) {
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        try {
            return ReportStatus.valueOf(value);
        } catch (IllegalArgumentException ex) {
            throw new AppException(ErrorCode.BAD_REQUEST, "Invalid status value");
        }
    }

    private UUID parseUuid(String value, String field) {
        try {
            return UUID.fromString(value);
        } catch (Exception ex) {
            throw new AppException(ErrorCode.BAD_REQUEST, "Invalid UUID for " + field);
        }
    }

    private UUID parseUuidNullable(String value, String field) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return parseUuid(value, field);
    }
}
