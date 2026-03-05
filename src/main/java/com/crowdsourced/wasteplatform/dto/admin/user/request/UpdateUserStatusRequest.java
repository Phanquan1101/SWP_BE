package com.crowdsourced.wasteplatform.dto.admin.user.request;

import com.crowdsourced.wasteplatform.entity.UserStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserStatusRequest {
    @NotNull
    private UserStatus status;

    private String reason;
}
