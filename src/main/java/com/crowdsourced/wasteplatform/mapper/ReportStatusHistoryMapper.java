package com.crowdsourced.wasteplatform.mapper;

import com.crowdsourced.wasteplatform.dto.report.response.ReportStatusHistoryResponse;
import com.crowdsourced.wasteplatform.entity.ReportStatusHistory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReportStatusHistoryMapper {
    ReportStatusHistoryResponse toResponse(ReportStatusHistory history);
}
