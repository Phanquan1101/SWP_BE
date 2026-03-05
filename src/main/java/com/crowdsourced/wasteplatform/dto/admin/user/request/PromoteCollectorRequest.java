package com.crowdsourced.wasteplatform.dto.admin.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PromoteCollectorRequest {
    @NotBlank
    private String areaId;
}
