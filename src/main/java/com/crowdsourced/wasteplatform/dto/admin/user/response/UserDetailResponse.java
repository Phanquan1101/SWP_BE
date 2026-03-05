package com.crowdsourced.wasteplatform.dto.admin.user.response;

import com.crowdsourced.wasteplatform.entity.UserStatus;
import java.time.Instant;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDetailResponse {
    String id;
    String email;
    String phone;
    String fullName;
    UserStatus status;
    List<String> roles;
    String workingAreaId;
    Instant createdAt;
    String suspendedReason;
    Instant updatedAt;
}
