package com.crowdsourced.wasteplatform.dto.auth.response;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserProfileResponse {

    private final UUID id;
    private final String email;
    private final String fullName;
    private final String userType;
    private final List<String> roles;
}
