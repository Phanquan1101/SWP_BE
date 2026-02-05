package com.crowdsourced.wasteplatform.mapper;

import com.crowdsourced.wasteplatform.dto.area.response.AreaResponse;
import com.crowdsourced.wasteplatform.entity.Area;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AreaMapper {

    @Mapping(target = "parentId", source = "parentId")
    AreaResponse toResponse(Area area);
}
