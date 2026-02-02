package com.crowdsourced.wasteplatform.dto.waste_report.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignCollectorRequest {
    @NotNull
    private String collectorId;
}
