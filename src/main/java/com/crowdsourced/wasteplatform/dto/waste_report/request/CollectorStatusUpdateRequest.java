package com.crowdsourced.wasteplatform.dto.waste_report.request;

import com.crowdsourced.wasteplatform.entity.CollectorStatus;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectorStatusUpdateRequest {
    @NotNull
    private CollectorStatus collectorStatus;

    private BigDecimal latitude;
    private BigDecimal longitude;
    private String note;
}
