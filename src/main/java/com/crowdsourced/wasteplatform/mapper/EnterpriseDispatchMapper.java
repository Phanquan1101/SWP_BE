package com.crowdsourced.wasteplatform.mapper;

import com.crowdsourced.wasteplatform.dto.enterprise.response.InboxReportItemResponse;
import com.crowdsourced.wasteplatform.entity.WasteReport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnterpriseDispatchMapper {

    @Mapping(target = "reportId", source = "id")
    @Mapping(target = "areaName", source = "area.name")
    @Mapping(target = "wasteCategoryName", source = "wasteCategory.name")
    InboxReportItemResponse toInboxItem(WasteReport report);
}
