package com.crowdsourced.wasteplatform.dto.admin.response;

import com.crowdsourced.wasteplatform.entity.Area;
import com.crowdsourced.wasteplatform.entity.Enterprise;
import com.crowdsourced.wasteplatform.entity.UserStatus;
import com.crowdsourced.wasteplatform.entity.UserType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAdminResponse {
    private UUID id;
    private String email;
    private String phone;
    private String fullName;
    private UserType userType;
    private UserStatus status;
    private String suspendedReason;

    private Enterprise enterprise;

    private Area area;

    private Instant createdAt;
}
