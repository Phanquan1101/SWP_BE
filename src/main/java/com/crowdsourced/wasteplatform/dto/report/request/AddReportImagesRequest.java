package com.crowdsourced.wasteplatform.dto.report.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.Data;

@Data
public class AddReportImagesRequest {

    @NotEmpty
    private List<@NotBlank String> imageUrls;
}
