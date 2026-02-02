package com.crowdsourced.wasteplatform.mapper;

import com.crowdsourced.wasteplatform.dto.capability.response.WasteCapabilityResponse;
import com.crowdsourced.wasteplatform.entity.WasteCapability;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WasteCapabilityMapper {

    @Mapping(target = "wasteCategoryId", source = "wasteCategoryId")
    @Mapping(target = "wasteCategoryCode", expression = "java(entity.getWasteCategory() != null ? entity.getWasteCategory().getCode() : null)")
    @Mapping(target = "wasteCategoryName", expression = "java(entity.getWasteCategory() != null ? entity.getWasteCategory().getName() : null)")
    @Mapping(target = "dailyCapacityKg", expression = "java(entity.getDailyCapacityKg().doubleValue())")
    WasteCapabilityResponse toResponse(WasteCapability entity);
}
