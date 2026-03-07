package com.crowdsourced.wasteplatform.dto.complaint.response;

import java.time.Instant;
import java.util.UUID;

import com.crowdsourced.wasteplatform.entity.ComplaintCategory;
import com.crowdsourced.wasteplatform.entity.ComplaintStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComplaintResponse {
    private UUID id;

    private UUID complainantId;

    private UUID reportId;

    private ComplaintCategory category;

    private String description;

    private java.math.BigDecimal latitude;

    private java.math.BigDecimal longitude;

    private ComplaintStatus status;

    private UUID resolvedBy;

    private String resolutionNote;

    private Instant createdAt;
   
    private Instant resolvedAt;
}
