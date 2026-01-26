package com.crowdsourced.wasteplatform.dto.auth.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LoginResponse {

    private final TokenResponse tokens;
    private final UserProfileResponse user;
}
