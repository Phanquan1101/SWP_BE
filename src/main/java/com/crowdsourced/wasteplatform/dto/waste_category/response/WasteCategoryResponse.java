package com.crowdsourced.wasteplatform.dto.waste_category.response;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WasteCategoryResponse {

    private final UUID id;
    private final String code;
    private final String name;
    private final boolean active;
}
