package com.crowdsourced.wasteplatform.dto.statistics.common;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeRangeRequest {

    @NotBlank
    private String range;
}
