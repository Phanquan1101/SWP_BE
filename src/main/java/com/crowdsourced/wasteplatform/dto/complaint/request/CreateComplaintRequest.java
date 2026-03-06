package com.crowdsourced.wasteplatform.dto.complaint.request;

import java.util.UUID;

import com.crowdsourced.wasteplatform.entity.ComplaintCategory;
import com.crowdsourced.wasteplatform.entity.ComplaintStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateComplaintRequest {

    private UUID reportId;

    private ComplaintCategory category;

    private String description;

    private java.math.BigDecimal latitude;

    private java.math.BigDecimal longitude;

    private ComplaintStatus status;

}
