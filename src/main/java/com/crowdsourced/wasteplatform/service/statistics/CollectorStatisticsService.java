package com.crowdsourced.wasteplatform.service.statistics;

import com.crowdsourced.wasteplatform.dto.statistics.collector.CollectorStatisticsOverviewResponse;
import com.crowdsourced.wasteplatform.entity.CollectorStatus;
import com.crowdsourced.wasteplatform.entity.MediaType;
import com.crowdsourced.wasteplatform.exception.AppException;
import com.crowdsourced.wasteplatform.exception.ErrorCode;
import com.crowdsourced.wasteplatform.repository.ReportAssignmentRepository;
import com.crowdsourced.wasteplatform.repository.ReportMediaRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CollectorStatisticsService {

    private final ReportAssignmentRepository reportAssignmentRepository;
    private final ReportMediaRepository reportMediaRepository;
    private final StatisticsRangeResolver rangeResolver;

    public CollectorStatisticsService(ReportAssignmentRepository reportAssignmentRepository,
                                      ReportMediaRepository reportMediaRepository,
                                      StatisticsRangeResolver rangeResolver) {
        this.reportAssignmentRepository = reportAssignmentRepository;
        this.reportMediaRepository = reportMediaRepository;
        this.rangeResolver = rangeResolver;
    }

    /**
     * Thong ke cho Collector:
     * - totalAssignedRequests: tong assignment duoc giao trong range
     * - totalCompletedRequests: assignment co collector_status = COLLECTED
     * - completionRate = completed/assigned, lam tron 2 chu so thap phan
     * - totalProofsUploaded: so media COLLECTED_PROOF do collector upload.
     *
     * Cac phep dem aggregate deu truy van DB-level de tranh stream tren list lon.
     */
    @Transactional(readOnly = true)
    public CollectorStatisticsOverviewResponse getOverview(String collectorId, String rangeRaw) {
        UUID collectorUuid = parseUuid(collectorId, "collectorId");
        StatisticsRangeWindow window = rangeResolver.resolve(rangeRaw);

        long totalAssignedRequests = reportAssignmentRepository.countAssignedByCollectorAndRange(
            collectorUuid, window.start(), window.end()
        );
        long totalCompletedRequests = reportAssignmentRepository.countByCollectorStatusAndRange(
            collectorUuid, CollectorStatus.COLLECTED, window.start(), window.end()
        );
        long totalProofsUploaded = reportMediaRepository.countByCollectorAndTypeAndRange(
            collectorUuid, MediaType.COLLECTED_PROOF, window.start(), window.end()
        );

        double completionRate = 0D;
        if (totalAssignedRequests > 0) {
            completionRate = BigDecimal.valueOf(totalCompletedRequests)
                .divide(BigDecimal.valueOf(totalAssignedRequests), 4, RoundingMode.HALF_UP)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
        }

        return CollectorStatisticsOverviewResponse.builder()
            .range(window.range().name())
            .totalAssignedRequests(totalAssignedRequests)
            .totalCompletedRequests(totalCompletedRequests)
            .completionRate(completionRate)
            .totalProofsUploaded(totalProofsUploaded)
            .build();
    }

    private UUID parseUuid(String value, String field) {
        try {
            return UUID.fromString(value);
        } catch (Exception ex) {
            throw new AppException(ErrorCode.BAD_REQUEST, "Invalid UUID for " + field);
        }
    }
}
