package com.crowdsourced.wasteplatform.dto.common.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancelRequest {

    @NotBlank(message = "reason must not be blank")
    @Size(min = 3, message = "reason must be at least 3 characters")
    private String reason;
}
