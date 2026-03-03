package com.crowdsourced.wasteplatform.dto.report.response;

import com.crowdsourced.wasteplatform.entity.MediaType;
import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ReportMediaResponse {
    UUID id;
    MediaType mediaType;
    String url;
    Instant takenAt;
    Instant createdAt;
}
