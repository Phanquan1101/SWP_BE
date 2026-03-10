package com.crowdsourced.wasteplatform.mapper;

import com.crowdsourced.wasteplatform.dto.voucher.response.VoucherRedemptionResponse;
import com.crowdsourced.wasteplatform.entity.VoucherRedemption;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VoucherRedemptionMapper {

    @Mapping(target = "redemptionId", source = "id")
    @Mapping(target = "voucherTitle", source = "voucher.title")
    VoucherRedemptionResponse toResponse(VoucherRedemption redemption);
}
