package com.crowdsourced.wasteplatform.repository.projection;

import java.time.Instant;

public interface CitizenRedeemedVoucherProjection {

    String getVoucherId();

    String getVoucherTitle();

    String getRedeemCode();

    Instant getRedeemedAt();

    String getStatus();
}
