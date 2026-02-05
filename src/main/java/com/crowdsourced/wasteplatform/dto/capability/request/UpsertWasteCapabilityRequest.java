package com.crowdsourced.wasteplatform.dto.capability.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpsertWasteCapabilityRequest {

    @NotNull
    @PositiveOrZero
    private Double dailyCapacityKg;

    private Boolean accepting;
}
