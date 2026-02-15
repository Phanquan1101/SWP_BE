package com.crowdsourced.wasteplatform.dto.report.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CancelWasteReportRequest {

    @NotBlank
    private String reason;
}
