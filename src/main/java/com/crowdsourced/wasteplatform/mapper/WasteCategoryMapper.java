package com.crowdsourced.wasteplatform.mapper;

import com.crowdsourced.wasteplatform.dto.waste_category.response.WasteCategoryResponse;
import com.crowdsourced.wasteplatform.entity.WasteCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WasteCategoryMapper {

    WasteCategoryResponse toResponse(WasteCategory wasteCategory);
}
