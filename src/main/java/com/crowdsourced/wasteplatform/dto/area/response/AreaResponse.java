package com.crowdsourced.wasteplatform.dto.area.response;

import lombok.Builder;
import lombok.Getter;
import java.util.UUID;

@Getter
@Builder
public class AreaResponse {

    private final UUID id;
    private final UUID parentId;
    private final String name;
    private final boolean active;
}
