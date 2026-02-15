package com.crowdsourced.wasteplatform.dto.report.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class CreateWasteReportRequest {

    @NotBlank
    private String areaId;

    @NotBlank
    private String wasteCategoryId;

    private String description;

    @NotNull
    @Positive
    private BigDecimal estimatedWeightKg;

    @NotNull
    @DecimalMin(value = "-90.0")
    @DecimalMax(value = "90.0")
    private BigDecimal latitude;

    @NotNull
    @DecimalMin(value = "-180.0")
    @DecimalMax(value = "180.0")
    private BigDecimal longitude;

    @Size(max = 255)
    private String addressText;

    private List<@NotBlank String> imageUrls;
}
