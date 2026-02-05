package com.crowdsourced.wasteplatform.mapper;

import com.crowdsourced.wasteplatform.dto.waste_report.response.WasteReportResponse;
import com.crowdsourced.wasteplatform.entity.ReportMedia;
import com.crowdsourced.wasteplatform.entity.WasteReport;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WasteReportMapper {

    @Mapping(target = "areaName", expression = "java(report.getArea()!=null?report.getArea().getName():null)")
    @Mapping(target = "wasteCategoryName", expression = "java(report.getWasteCategory()!=null?report.getWasteCategory().getName():null)")
    @Mapping(target = "status", source = "currentStatus")
    @Mapping(target = "mediaUrls", expression = "java(mapMedia(report.getMediaList()))")
    WasteReportResponse toResponse(WasteReport report);

    default List<String> mapMedia(List<ReportMedia> mediaList) {
        if (mediaList == null) return List.of();
        return mediaList.stream().map(ReportMedia::getUrl).collect(Collectors.toList());
    }
}
