package com.crowdsourced.wasteplatform.dto.complaint.request;

import com.crowdsourced.wasteplatform.entity.ComplaintStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResolveComplaintRequest {
    @NotNull
    private ComplaintStatus status;
    private String resolutionNote;
}
