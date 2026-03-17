package com.crowdsourced.wasteplatform.dto.statistics.citizen;

import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CitizenRedeemedVoucherItem {

    private UUID voucherId;
    private String voucherTitle;
    private String redeemCode;
    private Instant redeemedAt;
    private String status;
}
