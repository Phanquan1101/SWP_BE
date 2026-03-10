package com.crowdsourced.wasteplatform.dto.voucher.response;

import com.crowdsourced.wasteplatform.entity.VoucherDisplayStatus;
import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class VoucherResponse {
    UUID id;
    String code;
    String title;
    String description;
    Integer pointsCost;
    Integer stock;
    Instant availableFrom;
    Instant availableTo;
    boolean active;
    String imageUrl;
    Instant createdAt;
    Instant updatedAt;

    VoucherDisplayStatus displayStatus;
    Boolean canRedeem;
    Integer missingPoints;
    Long userCurrentPoints;
}
