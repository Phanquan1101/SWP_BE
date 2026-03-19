package com.crowdsourced.wasteplatform.dto.collector.request;

import com.crowdsourced.wasteplatform.entity.CollectorStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCollectorStatusRequest {

    @NotNull
    private CollectorStatus status;

    private String note;
}
