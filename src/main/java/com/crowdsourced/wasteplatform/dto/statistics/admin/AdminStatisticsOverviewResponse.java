package com.crowdsourced.wasteplatform.dto.statistics.admin;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminStatisticsOverviewResponse {

    private String range;
    private long totalActiveCollectors;
    private long totalActiveWasteCategories;
    private long totalComplaintsReceived;
    private long totalResolvedComplaints;
    private long totalActiveAreas;
    private long totalAcceptingWasteCategories;
}
