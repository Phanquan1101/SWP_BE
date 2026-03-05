package com.crowdsourced.wasteplatform.dto.enterprise.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CollectorPickResponse {
    String id;
    String fullName;
    String email;
    String areaId;
}
