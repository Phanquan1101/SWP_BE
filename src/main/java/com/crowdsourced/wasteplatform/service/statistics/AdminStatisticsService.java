package com.crowdsourced.wasteplatform.service.statistics;

import com.crowdsourced.wasteplatform.dto.statistics.admin.AdminCollectorsByAreaItem;
import com.crowdsourced.wasteplatform.dto.statistics.admin.AdminReportsByMonthItem;
import com.crowdsourced.wasteplatform.dto.statistics.admin.AdminStatisticsOverviewResponse;
import com.crowdsourced.wasteplatform.entity.ComplaintStatus;
import com.crowdsourced.wasteplatform.repository.AreaRepository;
import com.crowdsourced.wasteplatform.repository.ComplaintRepository;
import com.crowdsourced.wasteplatform.repository.UserRepository;
import com.crowdsourced.wasteplatform.repository.WasteCapabilityRepository;
import com.crowdsourced.wasteplatform.repository.WasteCategoryRepository;
import com.crowdsourced.wasteplatform.repository.WasteReportRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminStatisticsService {

    private static final String COLLECTOR_ROLE_CODE = "ROLE_COLLECTOR";

    private final UserRepository userRepository;
    private final WasteReportRepository wasteReportRepository;
    private final WasteCategoryRepository wasteCategoryRepository;
    private final WasteCapabilityRepository wasteCapabilityRepository;
    private final ComplaintRepository complaintRepository;
    private final AreaRepository areaRepository;
    private final StatisticsRangeResolver rangeResolver;

    public AdminStatisticsService(UserRepository userRepository,
                                  WasteReportRepository wasteReportRepository,
                                  WasteCategoryRepository wasteCategoryRepository,
                                  WasteCapabilityRepository wasteCapabilityRepository,
                                  ComplaintRepository complaintRepository,
                                  AreaRepository areaRepository,
                                  StatisticsRangeResolver rangeResolver) {
        this.userRepository = userRepository;
        this.wasteReportRepository = wasteReportRepository;
        this.wasteCategoryRepository = wasteCategoryRepository;
        this.wasteCapabilityRepository = wasteCapabilityRepository;
        this.complaintRepository = complaintRepository;
        this.areaRepository = areaRepository;
        this.rangeResolver = rangeResolver;
    }

    /**
     * Dashboard tong quan cho Admin.
     * users.area_id duoc hieu la working area cua collector, nen thong ke collector by area
     * duoc query truc tiep tu users + user_roles + areas de dam bao so lieu RBAC nhat quan.
     */
    @Transactional(readOnly = true)
    public AdminStatisticsOverviewResponse getOverview(String rangeRaw) {
        StatisticsRangeWindow window = rangeResolver.resolve(rangeRaw);

        long totalActiveCollectors = userRepository.countActiveCollectors(COLLECTOR_ROLE_CODE);
        long totalActiveWasteCategories = wasteCategoryRepository.countByActiveTrue();
        long totalComplaintsReceived = complaintRepository.countByCreatedAtBetween(window.start(), window.end());
        long totalResolvedComplaints = complaintRepository.countByStatusAndResolvedAtBetween(
            ComplaintStatus.RESOLVED, window.start(), window.end()
        );
        long totalActiveAreas = areaRepository.countByActiveTrue();
        long totalAcceptingWasteCategories = wasteCapabilityRepository.countByAcceptingTrue();

        return AdminStatisticsOverviewResponse.builder()
            .range(window.range().name())
            .totalActiveCollectors(totalActiveCollectors)
            .totalActiveWasteCategories(totalActiveWasteCategories)
            .totalComplaintsReceived(totalComplaintsReceived)
            .totalResolvedComplaints(totalResolvedComplaints)
            .totalActiveAreas(totalActiveAreas)
            .totalAcceptingWasteCategories(totalAcceptingWasteCategories)
            .build();
    }

    @Transactional(readOnly = true)
    public List<AdminCollectorsByAreaItem> getCollectorsByArea() {
        return userRepository.countCollectorsByArea(COLLECTOR_ROLE_CODE).stream()
            .map(row -> AdminCollectorsByAreaItem.builder()
                .areaId(UUID.fromString(row.getAreaId()))
                .areaName(row.getAreaName())
                .collectorCount(row.getCollectorCount())
                .build())
            .toList();
    }

    @Transactional(readOnly = true)
    public List<AdminReportsByMonthItem> getReportsByMonth(int year) {
        return wasteReportRepository.countReportsByMonth(year).stream()
            .map(row -> AdminReportsByMonthItem.builder()
                .month(row.getMonth())
                .reportCount(row.getReportCount())
                .build())
            .toList();
    }
}
