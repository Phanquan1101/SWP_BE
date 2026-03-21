package com.crowdsourced.wasteplatform.service.report;

import com.crowdsourced.wasteplatform.dto.common.PageResponse;
import com.crowdsourced.wasteplatform.dto.common.request.CancelRequest;
import com.crowdsourced.wasteplatform.dto.report.request.AddReportImagesRequest;
import com.crowdsourced.wasteplatform.dto.report.request.CreateWasteReportRequest;
import com.crowdsourced.wasteplatform.dto.report.response.ReportMediaResponse;
import com.crowdsourced.wasteplatform.dto.report.response.ReportStatusHistoryResponse;
import com.crowdsourced.wasteplatform.dto.report.response.WasteReportResponse;
import com.crowdsourced.wasteplatform.entity.Area;
import com.crowdsourced.wasteplatform.entity.CollectorStatus;
import com.crowdsourced.wasteplatform.entity.MediaType;
import com.crowdsourced.wasteplatform.entity.ReportMedia;
import com.crowdsourced.wasteplatform.entity.ReportStatus;
import com.crowdsourced.wasteplatform.entity.ReportStatusHistory;
import com.crowdsourced.wasteplatform.entity.User;
import com.crowdsourced.wasteplatform.entity.WasteCategory;
import com.crowdsourced.wasteplatform.entity.WasteReport;
import com.crowdsourced.wasteplatform.exception.AppException;
import com.crowdsourced.wasteplatform.exception.ErrorCode;
import com.crowdsourced.wasteplatform.mapper.ReportMediaMapper;
import com.crowdsourced.wasteplatform.mapper.ReportStatusHistoryMapper;
import com.crowdsourced.wasteplatform.mapper.WasteReportMapper;
import com.crowdsourced.wasteplatform.repository.AreaRepository;
import com.crowdsourced.wasteplatform.repository.ReportAssignmentRepository;
import com.crowdsourced.wasteplatform.repository.ReportMediaRepository;
import com.crowdsourced.wasteplatform.repository.ReportStatusHistoryRepository;
import com.crowdsourced.wasteplatform.repository.UserRepository;
import com.crowdsourced.wasteplatform.repository.WasteCategoryRepository;
import com.crowdsourced.wasteplatform.repository.WasteReportRepository;
import com.crowdsourced.wasteplatform.service.email.EmailService;
import java.time.Duration;
import java.time.Instant;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CitizenWasteReportService {

    private final WasteReportRepository reportRepository;
    private final AreaRepository areaRepository;
    private final WasteCategoryRepository categoryRepository;
    private final ReportAssignmentRepository assignmentRepository;
    private final ReportMediaRepository mediaRepository;
    private final ReportStatusHistoryRepository historyRepository;
    private final UserRepository userRepository;
    private final ReportMediaMapper mediaMapper;
    private final ReportStatusHistoryMapper historyMapper;
    private final WasteReportMapper reportMapper;
    private final EmailService emailService;

    @Transactional
    public WasteReportResponse createReport(CreateWasteReportRequest req, String citizenIdStr) {
        UUID citizenId = parseUuid(citizenIdStr, "citizenId");
        UUID areaId = parseUuid(req.getAreaId(), "areaId");
        UUID categoryId = parseUuid(req.getWasteCategoryId(), "wasteCategoryId");

        Area area = areaRepository.findById(areaId)
            .filter(Area::isActive)
            .orElseThrow(() -> new AppException(ErrorCode.AREA_NOT_FOUND, "Area not found or inactive"));
        WasteCategory category = categoryRepository.findById(categoryId)
            .filter(WasteCategory::isActive)
            .orElseThrow(() -> new AppException(ErrorCode.WASTE_CATEGORY_NOT_FOUND, "Waste category not found or inactive"));

        Instant now = Instant.now();
        Instant allowEditUntil = now.plus(Duration.ofMinutes(15));

        WasteReport report = WasteReport.builder()
            .citizenId(citizenId)
            .areaId(area.getId())
            .wasteCategoryId(category.getId())
            .description(req.getDescription())
            .estimatedWeightKg(req.getEstimatedWeightKg())
            .addressText(req.getAddressText())
            .currentStatus(ReportStatus.PENDING)
            .allowEditUntil(allowEditUntil)
            .build();
        WasteReport saved = reportRepository.save(report);

        if (req.getImageUrls() != null && !req.getImageUrls().isEmpty()) {
            for (String url : req.getImageUrls()) {
                mediaRepository.save(ReportMedia.builder()
                    .reportId(saved.getId())
                    .mediaType(MediaType.REPORT_IMAGE)
                    .url(url)
                    .createdBy(citizenId)
                    .build());
            }
        }

        historyRepository.save(ReportStatusHistory.builder()
            .reportId(saved.getId())
            .fromStatus(null)
            .toStatus(ReportStatus.PENDING)
            .note("Citizen created report")
            .changedBy(citizenId)
            .build());

        User user = userRepository.findById(citizenId)
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND, "User not found."));

        String subject = "Waste Report Submitted Successfully";
        String content =
            "Dear User,\n\n" +
            "Thank you for submitting your waste report to our platform.\n\n" +
            "We have successfully received your report and it is currently being reviewed by our administration team. " +
            "You will be notified once there is an update regarding its processing status.\n\n" +
            "Report Details:\n" +
            "- Description: " + req.getDescription() + "\n" +
            "- Location: " + req.getAddressText() + "\n" +
            "- Estimated Weight (kg): " + req.getEstimatedWeightKg() + "\n\n" +
            "We truly appreciate your contribution in helping us maintain a cleaner and healthier environment.\n\n" +
            "Best regards,\n" +
            "Waste Management Support Team\n" +
            "Crowdsourced Waste Platform";

        // Email la side-effect: tao report van thanh cong neu gui mail loi.
        try {
            emailService.sendComplaintResolvedEmail(user.getEmail(), subject, content);
        } catch (Exception ex) {
            log.warn("Cannot send report confirmation email for report {}", saved.getId(), ex);
        }

        return enrich(saved);
    }

    @Transactional(readOnly = true)
    public PageResponse<WasteReportResponse> getMyReports(String citizenIdStr, Pageable pageable) {
        UUID citizenId = parseUuid(citizenIdStr, "citizenId");
        Page<WasteReport> page = reportRepository.findAllByCitizenIdOrderByCreatedAtDesc(citizenId, pageable);
        Page<WasteReportResponse> mapped = page.map(this::enrich);
        return PageResponse.from(mapped);
    }

    @Transactional(readOnly = true)
    public WasteReportResponse getMyReportDetail(String reportId, String citizenIdStr) {
        WasteReport report = loadOwnedReport(reportId, citizenIdStr);
        return enrich(report);
    }

    @Transactional
    public WasteReportResponse cancelMyReport(String reportId, String citizenIdStr, CancelRequest req) {
        WasteReport report = loadOwnedReport(reportId, citizenIdStr);
        validateCancelReason(req.getReason());

        // Citizen chi duoc huy bao cao khi chua vao pha dieu phoi collector.
        if (report.getCurrentStatus() != ReportStatus.PENDING && report.getCurrentStatus() != ReportStatus.ACCEPTED) {
            throw new AppException(
                ErrorCode.INVALID_REPORT_STATUS_FOR_CANCEL,
                "Citizen chi duoc huy bao cao o trang thai PENDING hoac ACCEPTED"
            );
        }

        // Neu assignment dang hoat dong thi khoa huy de tranh conflict voi dieu phoi.
        boolean hasActiveAssignment = assignmentRepository.existsByReportIdAndCollectorStatusIn(
            report.getId(),
            EnumSet.of(CollectorStatus.ASSIGNED, CollectorStatus.ON_THE_WAY)
        );
        if (hasActiveAssignment) {
            throw new AppException(
                ErrorCode.INVALID_REPORT_STATUS_FOR_CANCEL,
                "Report dang co assignment active, khong the huy"
            );
        }

        ReportStatus fromStatus = report.getCurrentStatus();
        report.setCurrentStatus(ReportStatus.CANCELLED);
        reportRepository.save(report);

        historyRepository.save(ReportStatusHistory.builder()
            .reportId(report.getId())
            .fromStatus(fromStatus)
            .toStatus(ReportStatus.CANCELLED)
            .note(req.getReason().trim())
            .changedBy(parseUuid(citizenIdStr, "citizenId"))
            .build());

        return enrich(report);
    }

    @Transactional
    public WasteReportResponse addReportImages(String reportId, String citizenIdStr, AddReportImagesRequest req) {
        WasteReport report = loadOwnedReport(reportId, citizenIdStr);
        if (report.getCurrentStatus() != ReportStatus.PENDING) {
            throw new AppException(ErrorCode.INVALID_REPORT_STATUS, "Chi them anh khi bao cao dang PENDING");
        }
        for (String url : req.getImageUrls()) {
            mediaRepository.save(ReportMedia.builder()
                .reportId(report.getId())
                .mediaType(MediaType.REPORT_IMAGE)
                .url(url)
                .createdBy(parseUuid(citizenIdStr, "citizenId"))
                .build());
        }
        return enrich(report);
    }

    private WasteReport loadOwnedReport(String reportId, String citizenIdStr) {
        UUID id = parseUuid(reportId, "reportId");
        UUID citizenId = parseUuid(citizenIdStr, "citizenId");
        return reportRepository.findByIdAndCitizenId(id, citizenId)
            .orElseThrow(() -> new AppException(ErrorCode.REPORT_NOT_FOUND, "Report not found or not owned by user"));
    }

    private WasteReportResponse enrich(WasteReport report) {
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

    private UUID parseUuid(String value, String field) {
        try {
            return UUID.fromString(value);
        } catch (Exception ex) {
            throw new AppException(ErrorCode.BAD_REQUEST, "Invalid UUID for " + field);
        }
    }

    private void validateCancelReason(String reason) {
        // Bat buoc reason de minh bach audit va tra loi khi co tranh chap.
        if (reason == null || reason.trim().length() < 3) {
            throw new AppException(ErrorCode.CANCEL_REASON_REQUIRED, "Cancel reason must be at least 3 characters");
        }
    }
}
