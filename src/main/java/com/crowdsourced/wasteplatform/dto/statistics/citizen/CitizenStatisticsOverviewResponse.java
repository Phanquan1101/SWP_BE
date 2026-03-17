package com.crowdsourced.wasteplatform.dto.statistics.citizen;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CitizenStatisticsOverviewResponse {

    private long reportsSentThisMonth;
    private long totalVoucherRedemptions;
    private List<CitizenRedeemedVoucherItem> redeemedVouchers;
}
