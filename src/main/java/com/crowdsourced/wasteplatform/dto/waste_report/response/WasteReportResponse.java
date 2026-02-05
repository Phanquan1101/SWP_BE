package com.crowdsourced.wasteplatform.dto.waste_report.response;

import com.crowdsourced.wasteplatform.entity.ReportStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WasteReportResponse {
    private final UUID id;
    private final UUID areaId;
    private final UUID wasteCategoryId;
    private final String areaName;
    private final String wasteCategoryName;
    private final ReportStatus status;
    private final BigDecimal estimatedWeightKg;
    private final BigDecimal actualWeightKg;
    private final BigDecimal latitude;
    private final BigDecimal longitude;
    private final String addressText;
    private final String description;
    private final Instant createdAt;
    private final Instant updatedAt;
    private final List<String> mediaUrls;
}
