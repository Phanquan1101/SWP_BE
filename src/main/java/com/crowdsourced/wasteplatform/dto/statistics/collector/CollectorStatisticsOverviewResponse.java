package com.crowdsourced.wasteplatform.dto.statistics.collector;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CollectorStatisticsOverviewResponse {

    private String range;
    private long totalAssignedRequests;
    private long totalCompletedRequests;
    private double completionRate;
    private long totalProofsUploaded;
}
