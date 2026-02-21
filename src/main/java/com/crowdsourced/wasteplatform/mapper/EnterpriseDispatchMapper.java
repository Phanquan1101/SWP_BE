package com.crowdsourced.wasteplatform.mapper;

import com.crowdsourced.wasteplatform.dto.enterprise.response.InboxReportItemResponse;
import com.crowdsourced.wasteplatform.entity.WasteReport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnterpriseDispatchMapper {

    @Mapping(target = "reportId", source = "id")
    InboxReportItemResponse toInboxItem(WasteReport report);
}
