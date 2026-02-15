package com.crowdsourced.wasteplatform.mapper;

import com.crowdsourced.wasteplatform.dto.report.response.ReportMediaResponse;
import com.crowdsourced.wasteplatform.entity.ReportMedia;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReportMediaMapper {
    ReportMediaResponse toResponse(ReportMedia media);
}
