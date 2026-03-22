package com.crowdsourced.wasteplatform.dto.citizen.response;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CitizenProfileResponse {

    private String fullName;
    private String email;
    private String phone;
    private String area;
    private BigDecimal totalCollectedKg;
}
