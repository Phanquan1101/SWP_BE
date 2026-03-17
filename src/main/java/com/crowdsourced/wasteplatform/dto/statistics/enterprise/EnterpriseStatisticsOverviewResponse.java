package com.crowdsourced.wasteplatform.dto.statistics.enterprise;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EnterpriseStatisticsOverviewResponse {

    private String range;
    private long totalReportsReceived;
    private BigDecimal totalRecycledWeightKg;
    private long totalCompletedReports;
    private long totalAcceptedWasteCategories;
}
