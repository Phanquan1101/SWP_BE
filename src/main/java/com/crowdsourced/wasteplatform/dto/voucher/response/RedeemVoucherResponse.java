package com.crowdsourced.wasteplatform.dto.voucher.response;

import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RedeemVoucherResponse {
    UUID redemptionId;
    UUID voucherId;
    String voucherCode;
    String voucherTitle;
    String redeemCode;
    Instant redeemedAt;
    Long remainingPoints;
    String message;
}
