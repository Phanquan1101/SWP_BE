package com.crowdsourced.wasteplatform.service.statistics;

import com.crowdsourced.wasteplatform.dto.statistics.enterprise.EnterpriseStatisticsOverviewResponse;
import com.crowdsourced.wasteplatform.entity.ReportStatus;
import com.crowdsourced.wasteplatform.repository.WasteCapabilityRepository;
import com.crowdsourced.wasteplatform.repository.WasteReportRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnterpriseStatisticsService {

    private static final List<ReportStatus> RECEIVED_STATUSES = List.of(
        ReportStatus.ACCEPTED, ReportStatus.ASSIGNED, ReportStatus.ON_THE_WAY, ReportStatus.COLLECTED, ReportStatus.COMPLETED
    );
    private static final List<ReportStatus> COMPLETED_STATUSES = List.of(ReportStatus.COLLECTED, ReportStatus.COMPLETED);

    private final WasteReportRepository wasteReportRepository;
    private final WasteCapabilityRepository wasteCapabilityRepository;
    private final StatisticsRangeResolver rangeResolver;

    public EnterpriseStatisticsService(WasteReportRepository wasteReportRepository,
                                       WasteCapabilityRepository wasteCapabilityRepository,
                                       StatisticsRangeResolver rangeResolver) {
        this.wasteReportRepository = wasteReportRepository;
        this.wasteCapabilityRepository = wasteCapabilityRepository;
        this.rangeResolver = rangeResolver;
    }

    /**
     * Thong ke tong quan cho Enterprise Manager.
     * MVP chon cach tinh "duoc nhan" dua tren created_at + current_status >= ACCEPTED
     * de truy van nhanh tren DB index, tranh keo toan bo report len memory.
     */
    @Transactional(readOnly = true)
    public EnterpriseStatisticsOverviewResponse getOverview(String rangeRaw) {
        StatisticsRangeWindow window = rangeResolver.resolve(rangeRaw);

        long totalReportsReceived = wasteReportRepository.countByCreatedAtRangeAndStatuses(
            window.start(), window.end(), RECEIVED_STATUSES
        );
        long totalCompletedReports = wasteReportRepository.countByCreatedAtRangeAndStatuses(
            window.start(), window.end(), COMPLETED_STATUSES
        );
        BigDecimal totalRecycledWeightKg = wasteReportRepository.sumWeightByCreatedAtRangeAndStatuses(
            window.start(), window.end(), COMPLETED_STATUSES
        );
        long totalAcceptedWasteCategories = wasteCapabilityRepository.countByAcceptingTrue();

        return EnterpriseStatisticsOverviewResponse.builder()
            .range(window.range().name())
            .totalReportsReceived(totalReportsReceived)
            .totalRecycledWeightKg(totalRecycledWeightKg == null ? BigDecimal.ZERO : totalRecycledWeightKg)
            .totalCompletedReports(totalCompletedReports)
            .totalAcceptedWasteCategories(totalAcceptedWasteCategories)
            .build();
    }
}
