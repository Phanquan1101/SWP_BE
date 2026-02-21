package com.crowdsourced.wasteplatform.dto.enterprise.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RejectReportRequest {

    @NotBlank
    private String reason;
}
