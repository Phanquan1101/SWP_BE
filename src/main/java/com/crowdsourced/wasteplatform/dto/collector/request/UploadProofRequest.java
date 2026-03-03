package com.crowdsourced.wasteplatform.dto.collector.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;
import lombok.Data;

@Data
public class UploadProofRequest {

    @NotEmpty
    private List<@NotBlank String> proofUrls;

    private Instant takenAt;
}
