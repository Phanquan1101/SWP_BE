package com.crowdsourced.wasteplatform.dto.points.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PointBalanceResponse {

    private long currentPoints;
}

