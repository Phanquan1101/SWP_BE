package com.crowdsourced.wasteplatform.dto.enterprise.response;

import com.crowdsourced.wasteplatform.entity.ReportStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class InboxReportItemResponse {
    UUID reportId;
    UUID areaId;
    UUID wasteCategoryId;
    BigDecimal estimatedWeightKg;
    Instant createdAt;
    ReportStatus currentStatus;
    UUID citizenId;
    String addressText;
}
