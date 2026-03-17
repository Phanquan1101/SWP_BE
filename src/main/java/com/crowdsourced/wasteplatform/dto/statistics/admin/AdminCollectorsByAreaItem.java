package com.crowdsourced.wasteplatform.dto.statistics.admin;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminCollectorsByAreaItem {

    private UUID areaId;
    private String areaName;
    private long collectorCount;
}
