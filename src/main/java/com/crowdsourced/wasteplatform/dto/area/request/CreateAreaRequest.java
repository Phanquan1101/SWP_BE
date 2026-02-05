package com.crowdsourced.wasteplatform.dto.area.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAreaRequest {

    private String parentId; // optional UUID string

    @NotBlank
    private String name;
}
