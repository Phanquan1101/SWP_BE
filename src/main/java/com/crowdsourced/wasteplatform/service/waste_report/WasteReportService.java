package com.crowdsourced.wasteplatform.service.waste_report;

import com.crowdsourced.wasteplatform.dto.waste_report.request.*;
import com.crowdsourced.wasteplatform.dto.waste_report.response.WasteReportResponse;
import com.crowdsourced.wasteplatform.entity.Area;
import com.crowdsourced.wasteplatform.entity.CollectorStatus;
import com.crowdsourced.wasteplatform.entity.ReportAssignment;
import com.crowdsourced.wasteplatform.entity.ReportMedia;
import com.crowdsourced.wasteplatform.entity.ReportStatus;
import com.crowdsourced.wasteplatform.entity.ReportStatusHistory;
import com.crowdsourced.wasteplatform.entity.User;
import com.crowdsourced.wasteplatform.entity.UserType;
import com.crowdsourced.wasteplatform.entity.WasteReport;
import com.crowdsourced.wasteplatform.exception.AppException;
import com.crowdsourced.wasteplatform.exception.ErrorCode;
import com.crowdsourced.wasteplatform.mapper.WasteReportMapper;
import com.crowdsourced.wasteplatform.repository.AreaRepository;
import com.crowdsourced.wasteplatform.repository.ReportAssignmentRepository;
import com.crowdsourced.wasteplatform.repository.ReportMediaRepository;
import com.crowdsourced.wasteplatform.repository.ReportStatusHistoryRepository;
import com.crowdsourced.wasteplatform.repository.UserRepository;
import com.crowdsourced.wasteplatform.repository.WasteCategoryRepository;
import com.crowdsourced.wasteplatform.repository.WasteReportRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WasteReportService {

    private final WasteReportRepository reportRepository;
    private final ReportMediaRepository mediaRepository;
    private final ReportStatusHistoryRepository historyRepository;
    private final ReportAssignmentRepository assignmentRepository;
    private final AreaRepository areaRepository;
    private final WasteCategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final WasteReportMapper mapper;

    @Transactional
    public WasteReportResponse createForCitizen(UUID citizenId, CreateWasteReportRequest request) {
        UUID areaId = parseUuid(request.getAreaId(), "areaId");
        UUID categoryId = parseUuid(request.getWasteCategoryId(), "wasteCategoryId");

        areaRepository.findById(areaId)
            .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Area not found"));
        categoryRepository.findById(categoryId)
            .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Waste category not found"));

        WasteReport report = WasteReport.builder()
            .citizenId(citizenId)
            .areaId(areaId)
            .wasteCategoryId(categoryId)
            .description(request.getDescription())
            .estimatedWeightKg(request.getEstimatedWeightKg())
            .latitude(request.getLatitude())
            .longitude(request.getLongitude())
            .addressText(request.getAddressText())
            .currentStatus(ReportStatus.PENDING)
            .build();
        WasteReport saved = reportRepository.save(report);

        // Lưu media (ảnh hiện trường) nếu người dùng đính kèm
        if (request.getMediaUrls() != null) {
            for (String url : request.getMediaUrls()) {
                mediaRepository.save(ReportMedia.builder()
                    .reportId(saved.getId())
                    .mediaType(com.crowdsourced.wasteplatform.entity.MediaType.REPORT_IMAGE)
                    .url(url)
                    .build());
            }
        }

        historyRepository.save(ReportStatusHistory.builder()
            .reportId(saved.getId())
            .fromStatus(null)
            .toStatus(ReportStatus.PENDING)
            .changedBy(citizenId)
            .note("Citizen tạo báo cáo mới")
            .build());

        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = false)
    public WasteReportResponse updateReportForCitizen(UUID citizenId, UpdateWasteReportRequest request, UUID reportId) {

        UUID areaId = parseUuid(request.getAreaId(), "areaId");
        UUID categoryId = parseUuid(request.getWasteCategoryId(), "wasteCategoryId");
        areaRepository.findById(areaId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Area not found"));
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Waste category not found"));

        WasteReport saved = loadReport(reportId);

        if(saved.getCurrentStatus().equals(ReportStatus.PENDING)) {
            WasteReport report = saved.toBuilder()
                    .citizenId(citizenId)
                    .areaId(areaId)
                    .wasteCategoryId(categoryId)
                    .description(request.getDescription())
                    .estimatedWeightKg(request.getEstimatedWeightKg())
                    .latitude(request.getLatitude())
                    .longitude(request.getLongitude())
                    .addressText(request.getAddressText())
                    .currentStatus(ReportStatus.PENDING)
                    .build();
            saved = reportRepository.save(report);

            // Lưu media (ảnh hiện trường) nếu người dùng đính kèm
            if (request.getMediaUrls() != null) {
                for (String url : request.getMediaUrls()) {
                    mediaRepository.save(ReportMedia.builder()
                            .reportId(saved.getId())
                            .mediaType(com.crowdsourced.wasteplatform.entity.MediaType.REPORT_IMAGE)
                            .url(url)
                            .build());
                }
            }

            historyRepository.save(ReportStatusHistory.builder()
                    .reportId(saved.getId())
                    .fromStatus(ReportStatus.PENDING)
                    .toStatus(ReportStatus.PENDING)
                    .changedBy(citizenId)
                    .note("Citizen sửa báo cáo")
                    .build());
        }


        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<WasteReportResponse> listByCitizen(UUID citizenId) {
        return reportRepository.findByCitizenIdOrderByCreatedAtDesc(citizenId)
            .stream()
            .map(mapper::toResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public List<WasteReportResponse> listByManage(UUID manageID) {
        User manageUser = userRepository.findById(manageID)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Manage user not found"));
        if (!manageUser.getUserType().equals(UserType.ENTERPRISE_MANAGER)) {
            throw new AppException(ErrorCode.FORBIDDEN, "User is not a manageUser");
        }

        return reportRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<WasteReportResponse> listByArea(UUID manageID, UUID areaId) {
        areaRepository.findById(areaId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Area not found"));

        User manageUser = userRepository.findById(manageID).
                orElseThrow(() ->  new AppException(ErrorCode.NOT_FOUND, "manageUser not found"));

        if (!manageUser.getUserType().equals(UserType.ENTERPRISE_MANAGER)) {
            throw new AppException(ErrorCode.FORBIDDEN, "User is not a manageUser");
        }
        return reportRepository.findByAreaId(areaId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
    @Transactional(readOnly = true)
    public WasteReportResponse getByCitizen(UUID citizenId, UUID reportId) {
        WasteReport report = reportRepository.findById(reportId)
            .filter(r -> citizenId.equals(r.getCitizenId()))
            .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Report not found"));
        return mapper.toResponse(report);
    }

    @Transactional
    public WasteReportResponse accept(UUID managerId, UUID reportId) {
        WasteReport report = loadReport(reportId);
        if (report.getCurrentStatus() != ReportStatus.PENDING) {
            throw new AppException(ErrorCode.CONFLICT, "Chỉ nhận báo cáo ở trạng thái PENDING");
        }
        changeStatus(report, ReportStatus.ACCEPTED, managerId, "Manager duyệt báo cáo");
        return mapper.toResponse(reportRepository.save(report));
    }

    @Transactional
    public WasteReportResponse reject(UUID managerId, UUID reportId, RejectReportRequest req) {
        WasteReport report = loadReport(reportId);
        if (report.getCurrentStatus() != ReportStatus.PENDING) {
            throw new AppException(ErrorCode.CONFLICT, "Chỉ từ chối báo cáo ở trạng thái PENDING");
        }
        changeStatus(report, ReportStatus.REJECTED, managerId, req.getReason());
        return mapper.toResponse(reportRepository.save(report));
    }

    @Transactional
    public WasteReportResponse assign(UUID managerId, UUID reportId, AssignCollectorRequest req) {
        WasteReport report = loadReport(reportId);
        if (report.getCurrentStatus() != ReportStatus.ACCEPTED) {
            throw new AppException(ErrorCode.CONFLICT, "Chỉ assign khi báo cáo đã ACCEPTED");
        }
        UUID collectorId = parseUuid(req.getCollectorId(), "collectorId");
        User collector = userRepository.findById(collectorId)
            .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Collector không tồn tại"));
        if (collector.getUserType() != UserType.COLLECTOR) {
            throw new AppException(ErrorCode.FORBIDDEN, "User không phải collector");
        }
        if (collector.getAreaId() == null || !collector.getAreaId().equals(report.getAreaId())) {
            throw new AppException(ErrorCode.FORBIDDEN, "Collector không thuộc khu vực của báo cáo");
        }

        assignmentRepository.save(ReportAssignment.builder()
            .reportId(report.getId())
            .collectorId(collectorId)
            .assignedBy(managerId)
            .collectorStatus(CollectorStatus.ASSIGNED)
            .build());

        changeStatus(report, ReportStatus.ASSIGNED, managerId, "Assign collector");
        return mapper.toResponse(reportRepository.save(report));
    }

    @Transactional
    public WasteReportResponse collectorUpdate(UUID collectorId, UUID assignmentId, CollectorStatusUpdateRequest req) {
        ReportAssignment assignment = assignmentRepository.findById(assignmentId)
            .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Assignment không tồn tại"));
        if (!collectorId.equals(assignment.getCollectorId())) {
            throw new AppException(ErrorCode.FORBIDDEN, "Không phải collector được giao");
        }
        assignment.setCollectorStatus(req.getCollectorStatus());
        assignment.setLastKnownLatitude(req.getLatitude());
        assignment.setLastKnownLongitude(req.getLongitude());
        assignmentRepository.save(assignment);

        WasteReport report = loadReport(assignment.getReportId());
        // Map collector status sang report status (??n gi?n)
        ReportStatus toStatus = switch (req.getCollectorStatus()) {
            case ASSIGNED -> ReportStatus.ASSIGNED;
            case ON_THE_WAY -> ReportStatus.ON_THE_WAY;
            case COLLECTED -> ReportStatus.COLLECTED;
            case FAILED -> ReportStatus.REJECTED;
        };
        changeStatus(report, toStatus, collectorId, req.getNote());
        return mapper.toResponse(reportRepository.save(report));
    }

    private WasteReport loadReport(UUID id) {
        return reportRepository.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Report kh?ng t?n t?i"));
    }

    private void changeStatus(WasteReport report, ReportStatus toStatus, UUID actorId, String note) {
        ReportStatus from = report.getCurrentStatus();
        report.setCurrentStatus(toStatus);
        historyRepository.save(ReportStatusHistory.builder()
            .reportId(report.getId())
            .fromStatus(from)
            .toStatus(toStatus)
            .changedBy(actorId)
            .note(note)
            .build());
    }

    private UUID parseUuid(String value, String field) {
        try {
            return UUID.fromString(value);
        } catch (Exception ex) {
            throw new AppException(ErrorCode.BAD_REQUEST, "Invalid UUID for " + field);
        }
    }
}
