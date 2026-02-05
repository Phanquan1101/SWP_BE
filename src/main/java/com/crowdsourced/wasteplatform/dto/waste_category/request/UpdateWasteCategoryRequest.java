package com.crowdsourced.wasteplatform.dto.waste_category.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateWasteCategoryRequest {

    @NotBlank
    private String name;
}
