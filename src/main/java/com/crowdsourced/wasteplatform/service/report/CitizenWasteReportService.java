package com.crowdsourced.wasteplatform.service.report;

import com.crowdsourced.wasteplatform.dto.common.PageResponse;
import com.crowdsourced.wasteplatform.dto.report.request.AddReportImagesRequest;
import com.crowdsourced.wasteplatform.dto.report.request.CancelWasteReportRequest;
import com.crowdsourced.wasteplatform.dto.report.request.CreateWasteReportRequest;
import com.crowdsourced.wasteplatform.dto.report.response.ReportMediaResponse;
import com.crowdsourced.wasteplatform.dto.report.response.ReportStatusHistoryResponse;
import com.crowdsourced.wasteplatform.dto.report.response.WasteReportResponse;
import com.crowdsourced.wasteplatform.entity.Area;
import com.crowdsourced.wasteplatform.entity.MediaType;
import com.crowdsourced.wasteplatform.entity.ReportMedia;
import com.crowdsourced.wasteplatform.entity.ReportStatus;
import com.crowdsourced.wasteplatform.entity.ReportStatusHistory;
import com.crowdsourced.wasteplatform.entity.WasteCategory;
import com.crowdsourced.wasteplatform.entity.WasteReport;
import com.crowdsourced.wasteplatform.exception.AppException;
import com.crowdsourced.wasteplatform.exception.ErrorCode;
import com.crowdsourced.wasteplatform.mapper.ReportMediaMapper;
import com.crowdsourced.wasteplatform.mapper.ReportStatusHistoryMapper;
import com.crowdsourced.wasteplatform.mapper.WasteReportMapper;
import com.crowdsourced.wasteplatform.repository.AreaRepository;
import com.crowdsourced.wasteplatform.repository.ReportMediaRepository;
import com.crowdsourced.wasteplatform.repository.ReportStatusHistoryRepository;
import com.crowdsourced.wasteplatform.repository.WasteCategoryRepository;
import com.crowdsourced.wasteplatform.repository.WasteReportRepository;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CitizenWasteReportService {

    private final WasteReportRepository reportRepository;
    private final AreaRepository areaRepository;
    private final WasteCategoryRepository categoryRepository;
    private final ReportMediaRepository mediaRepository;
    private final ReportStatusHistoryRepository historyRepository;
    private final ReportMediaMapper mediaMapper;
    private final ReportStatusHistoryMapper historyMapper;
    private final WasteReportMapper reportMapper;

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
            .latitude(req.getLatitude())
            .longitude(req.getLongitude())
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
    public WasteReportResponse cancelMyReport(String reportId, String citizenIdStr, CancelWasteReportRequest req) {
        WasteReport report = loadOwnedReport(reportId, citizenIdStr);
        if (report.getCurrentStatus() != ReportStatus.PENDING && report.getCurrentStatus() != ReportStatus.ACCEPTED) {
            throw new AppException(ErrorCode.INVALID_REPORT_STATUS, "Chỉ hủy khi báo cáo đang PENDING hoặc ACCEPTED");
        }
        // Nếu đã được assign collector thì không cho hủy
        if (report.getCurrentStatus() == ReportStatus.ASSIGNED || report.getCurrentStatus() == ReportStatus.ON_THE_WAY
            || report.getCurrentStatus() == ReportStatus.COLLECTED || report.getCurrentStatus() == ReportStatus.COMPLETED) {
            throw new AppException(ErrorCode.INVALID_REPORT_STATUS, "Báo cáo đã được xử lý, không thể hủy");
        }

        ReportStatus fromStatus = report.getCurrentStatus();
        report.setCurrentStatus(ReportStatus.CANCELLED);
        reportRepository.save(report);

        historyRepository.save(ReportStatusHistory.builder()
            .reportId(report.getId())
            .fromStatus(fromStatus)
            .toStatus(ReportStatus.CANCELLED)
            .note(req.getReason())
            .changedBy(parseUuid(citizenIdStr, "citizenId"))
            .build());

        return enrich(report);
    }

    @Transactional
    public WasteReportResponse addReportImages(String reportId, String citizenIdStr, AddReportImagesRequest req) {
        WasteReport report = loadOwnedReport(reportId, citizenIdStr);
        if (report.getCurrentStatus() != ReportStatus.PENDING) {
            throw new AppException(ErrorCode.INVALID_REPORT_STATUS, "Chỉ thêm ảnh khi báo cáo đang PENDING");
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
}
