package com.crowdsourced.wasteplatform.mapper;

import com.crowdsourced.wasteplatform.dto.report.response.ReportMediaResponse;
import com.crowdsourced.wasteplatform.dto.report.response.ReportStatusHistoryResponse;
import com.crowdsourced.wasteplatform.dto.report.response.WasteReportResponse;
import com.crowdsourced.wasteplatform.entity.WasteReport;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {
    ReportMediaMapper.class,
    ReportStatusHistoryMapper.class
})
public interface WasteReportMapper {

    @Mapping(target = "media", source = "mediaList")
    @Mapping(target = "statusHistory", source = "statusHistory")
    WasteReportResponse toResponse(WasteReport report,
                                   List<ReportMediaResponse> mediaList,
                                   List<ReportStatusHistoryResponse> statusHistory);

    default WasteReportResponse toResponse(WasteReport report) {
        return toResponse(report, List.of(), List.of());
    }
}
