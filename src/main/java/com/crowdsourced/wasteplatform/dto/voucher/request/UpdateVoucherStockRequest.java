package com.crowdsourced.wasteplatform.dto.voucher.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateVoucherStockRequest {

    @NotNull
    @PositiveOrZero
    private Integer stock;
}
