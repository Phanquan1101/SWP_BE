package com.crowdsourced.wasteplatform.service.admin;

import com.crowdsourced.wasteplatform.dto.admin.request.UpsertUserRequest;
import com.crowdsourced.wasteplatform.dto.admin.response.UserAdminResponse;
import com.crowdsourced.wasteplatform.dto.auth.request.RegisterRequest;
import com.crowdsourced.wasteplatform.dto.auth.response.LoginResponse;
import com.crowdsourced.wasteplatform.dto.auth.response.UserProfileResponse;
import com.crowdsourced.wasteplatform.entity.User;
import com.crowdsourced.wasteplatform.exception.PageResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface AdminUserService {
    LoginResponse register(RegisterRequest request);

    UserProfileResponse updatedProfile(UUID userId, UpsertUserRequest request);

    PageResponse<UserAdminResponse> getUsers(
            String keyword,
            User.UserType userType,
            User.UserStatus status,
            int page,
            int size
    );
    UserAdminResponse getUser(UUID userId);

}
