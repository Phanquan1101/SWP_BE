package com.crowdsourced.wasteplatform.dto.report.response;

import com.crowdsourced.wasteplatform.entity.ReportStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class WasteReportResponse {
    UUID id;
    UUID citizenId;
    UUID areaId;
    String areaName;
    UUID wasteCategoryId;
    String wasteCategoryName;
    String description;
    BigDecimal estimatedWeightKg;
    BigDecimal actualWeightKg;
    String addressText;
    ReportStatus currentStatus;
    Instant createdAt;
    Instant updatedAt;
    List<ReportMediaResponse> media;
    List<ReportStatusHistoryResponse> statusHistory;
}
