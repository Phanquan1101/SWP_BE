package com.crowdsourced.wasteplatform.mapper;

import com.crowdsourced.wasteplatform.dto.points.response.PointTransactionResponse;
import com.crowdsourced.wasteplatform.entity.PointTransaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PointTransactionMapper {
    PointTransactionResponse toResponse(PointTransaction entity);
}
