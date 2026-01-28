package com.crowdsourced.wasteplatform.dto.admin.request;

import com.crowdsourced.wasteplatform.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpsertUserRequest {
    private String email;

    private String phone;

    private String fullName;

    private User.UserStatus status;

    private String suspendedReason;

    private UUID enterpriseId;

    private UUID areaId;

    private List<String> roles;
}
