package com.crowdsourced.wasteplatform.dto.capability.response;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WasteCapabilityResponse {

    private final UUID id;
    private final UUID wasteCategoryId;
    private final String wasteCategoryCode;
    private final String wasteCategoryName;
    private final double dailyCapacityKg;
    private final boolean accepting;
}
