package com.crowdsourced.wasteplatform.dto.voucher.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateVoucherRequest {

    @NotBlank
    private String title;

    private String description;

    @NotNull
    @PositiveOrZero
    private Integer pointsCost;

    @NotNull
    @PositiveOrZero
    private Integer stock;

    private Instant availableFrom;
    private Instant availableTo;

    private Boolean isActive;

    private String imageUrl;
}
