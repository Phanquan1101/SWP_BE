package com.crowdsourced.wasteplatform.dto.report.response;

import com.crowdsourced.wasteplatform.entity.ReportStatus;
import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ReportStatusHistoryResponse {
    UUID id;
    ReportStatus fromStatus;
    ReportStatus toStatus;
    String note;
    UUID changedBy;
    Instant createdAt;
}
