package com.crowdsourced.wasteplatform.dto.area.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAreaRequest {

    private String parentId;

    @NotBlank
    private String name;
}
