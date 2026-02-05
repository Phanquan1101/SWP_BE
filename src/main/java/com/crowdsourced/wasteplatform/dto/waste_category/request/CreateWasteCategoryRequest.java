package com.crowdsourced.wasteplatform.dto.waste_category.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateWasteCategoryRequest {

    @NotBlank
    private String code;

    @NotBlank
    private String name;
}
