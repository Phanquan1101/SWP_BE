package com.crowdsourced.wasteplatform.dto.points.response;

import com.crowdsourced.wasteplatform.entity.TxType;
import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PointTransactionResponse {
    UUID id;
    UUID reportId;
    TxType txType;
    Integer points;
    String description;
    Instant createdAt;
}
