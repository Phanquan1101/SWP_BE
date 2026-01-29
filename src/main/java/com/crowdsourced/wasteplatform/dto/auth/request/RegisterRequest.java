package com.crowdsourced.wasteplatform.dto.auth.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RegisterRequest {
    private String fullName;
    private String email;
    private String phone;
    private String password;
    @NotNull
    private UUID areaId;
}
