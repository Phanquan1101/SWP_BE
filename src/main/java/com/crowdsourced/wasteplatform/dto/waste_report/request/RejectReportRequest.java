package com.crowdsourced.wasteplatform.dto.waste_report.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RejectReportRequest {
    @NotBlank
    private String reason;
}
