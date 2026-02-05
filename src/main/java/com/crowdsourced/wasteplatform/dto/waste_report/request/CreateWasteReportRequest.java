package com.crowdsourced.wasteplatform.dto.waste_report.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateWasteReportRequest {
    @NotNull
    private String areaId;

    @NotNull
    private String wasteCategoryId;

    private String description;

    @NotNull
    @DecimalMin(value = "0.001")
    private BigDecimal estimatedWeightKg;

    @NotNull
    @DecimalMin(value = "-90")
    @DecimalMax(value = "90")
    private BigDecimal latitude;

    @NotNull
    @DecimalMin(value = "-180")
    @DecimalMax(value = "180")
    private BigDecimal longitude;

    private String addressText;

    private List<String> mediaUrls;
}
