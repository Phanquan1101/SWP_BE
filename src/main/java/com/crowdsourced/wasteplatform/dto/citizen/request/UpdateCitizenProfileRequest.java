package com.crowdsourced.wasteplatform.dto.citizen.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateCitizenProfileRequest {

    @Size(min = 2, max = 150)
    private String fullName;

    @Size(max = 30)
    private String phone;

    private String areaId;
}
