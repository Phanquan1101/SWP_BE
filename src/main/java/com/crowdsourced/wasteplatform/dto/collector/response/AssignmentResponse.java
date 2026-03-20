package com.crowdsourced.wasteplatform.dto.collector.response;

import com.crowdsourced.wasteplatform.entity.CollectorStatus;
import com.crowdsourced.wasteplatform.entity.ReportStatus;
import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AssignmentResponse {
    UUID assignmentId;
    UUID reportId;
    CollectorStatus collectorStatus;
    Instant assignedAt;

    UUID areaId;
    String areaName;
    UUID wasteCategoryId;
    String wasteCategoryName;
    String addressText;
    ReportStatus currentStatus;
}
