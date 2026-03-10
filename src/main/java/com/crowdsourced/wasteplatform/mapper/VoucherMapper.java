package com.crowdsourced.wasteplatform.mapper;

import com.crowdsourced.wasteplatform.dto.voucher.response.VoucherResponse;
import com.crowdsourced.wasteplatform.entity.Voucher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VoucherMapper {

    VoucherResponse toResponse(Voucher voucher);
}
