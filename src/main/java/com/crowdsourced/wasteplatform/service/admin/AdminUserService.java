package com.crowdsourced.wasteplatform.service.admin;

import com.crowdsourced.wasteplatform.dto.admin.user.request.PromoteCollectorRequest;
import com.crowdsourced.wasteplatform.dto.admin.user.request.UpdateUserStatusRequest;
import com.crowdsourced.wasteplatform.dto.admin.user.response.UserDetailResponse;
import com.crowdsourced.wasteplatform.dto.admin.user.response.UserSummaryResponse;
import com.crowdsourced.wasteplatform.dto.common.PageResponse;
import com.crowdsourced.wasteplatform.entity.Area;
import com.crowdsourced.wasteplatform.entity.Role;
import com.crowdsourced.wasteplatform.entity.User;
import com.crowdsourced.wasteplatform.entity.UserRole;
import com.crowdsourced.wasteplatform.entity.UserStatus;
import com.crowdsourced.wasteplatform.entity.UserType;
import com.crowdsourced.wasteplatform.exception.AppException;
import com.crowdsourced.wasteplatform.exception.ErrorCode;
import com.crowdsourced.wasteplatform.mapper.AdminMapper;
import com.crowdsourced.wasteplatform.repository.AreaRepository;
import com.crowdsourced.wasteplatform.repository.RoleRepository;
import com.crowdsourced.wasteplatform.repository.UserRepository;
import com.crowdsourced.wasteplatform.repository.UserRoleRepository;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_ENTERPRISE_MANAGER = "ROLE_ENTERPRISE_MANAGER";
    private static final String ROLE_COLLECTOR = "ROLE_COLLECTOR";
    private static final String ROLE_CITIZEN = "ROLE_CITIZEN";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final AreaRepository areaRepository;
    private final AdminMapper adminMapper;

    @Transactional(readOnly = true)
    public PageResponse<UserSummaryResponse> searchUsers(String q, String roleCode, String status, Pageable pageable) {
        String normalizedQuery = normalizeBlank(q);
        String normalizedRoleCode = normalizeRoleCode(roleCode);
        UserStatus parsedStatus = parseUserStatus(status);

        Page<UserSummaryResponse> page = userRepository.searchUsers(normalizedQuery, normalizedRoleCode, parsedStatus, pageable)
            .map(this::toSummary);
        return PageResponse.from(page);
    }

    @Transactional(readOnly = true)
    public UserDetailResponse getUser(String userId) {
        return toDetail(loadUser(userId));
    }

    @Transactional
    public UserDetailResponse updateStatus(String userId, UpdateUserStatusRequest req, String adminId) {
        parseUuid(adminId, "adminId");
        User user = loadUser(userId);
        if (req.getStatus() == UserStatus.SUSPENDED) {
            String reason = normalizeBlank(req.getReason());
            if (reason == null) {
                throw new AppException(ErrorCode.BAD_REQUEST, "Suspended reason is required");
            }
            user.setSuspendedReason(reason);
        } else {
            user.setSuspendedReason(null);
        }
        user.setStatus(req.getStatus());
        return toDetail(userRepository.save(user));
    }

    @Transactional
    public UserDetailResponse assignRole(String userId, String roleCode, String adminId) {
        parseUuid(adminId, "adminId");
        User user = loadUser(userId);
        ensureUserNotSuspended(user);

        String normalizedRoleCode = normalizeRoleCodeRequired(roleCode);
        Role role = loadRole(normalizedRoleCode);
        if (ROLE_COLLECTOR.equals(normalizedRoleCode) && user.getAreaId() == null) {
            throw new AppException(ErrorCode.WORKING_AREA_REQUIRED, "Collector must have working area before role assignment");
        }
        if (!userRoleRepository.existsByUserIdAndRoleId(user.getId(), role.getId())) {
            userRoleRepository.save(UserRole.builder()
                .userId(user.getId())
                .roleId(role.getId())
                .build());
        }
        return syncUserTypeAndReturn(user);
    }

    @Transactional
    public UserDetailResponse removeRole(String userId, String roleCode, String adminId) {
        UUID adminUuid = parseUuid(adminId, "adminId");
        User user = loadUser(userId);
        String normalizedRoleCode = normalizeRoleCodeRequired(roleCode);
        Role role = loadRole(normalizedRoleCode);

        if (ROLE_ADMIN.equals(normalizedRoleCode) && user.getId().equals(adminUuid)) {
            throw new AppException(ErrorCode.SELF_ADMIN_ROLE_PROTECTED, "Cannot remove your own admin role");
        }

        userRoleRepository.deleteByUserIdAndRoleId(user.getId(), role.getId());
        if (ROLE_COLLECTOR.equals(normalizedRoleCode)) {
            user.setAreaId(null);
        }
        return syncUserTypeAndReturn(user);
    }

    @Transactional
    public UserDetailResponse setWorkingArea(String userId, String areaId, String adminId) {
        parseUuid(adminId, "adminId");
        User user = loadUser(userId);
        if (!hasRole(user.getId(), ROLE_COLLECTOR)) {
            throw new AppException(ErrorCode.COLLECTOR_ROLE_REQUIRED, "User must have collector role before setting working area");
        }
        Area area = loadActiveArea(areaId);
        user.setAreaId(area.getId());
        return toDetail(userRepository.save(user));
    }

    @Transactional
    public UserDetailResponse clearWorkingArea(String userId, String adminId) {
        parseUuid(adminId, "adminId");
        User user = loadUser(userId);
        if (hasRole(user.getId(), ROLE_COLLECTOR)) {
            throw new AppException(ErrorCode.CANNOT_CLEAR_AREA_WHILE_COLLECTOR, "Remove collector role before clearing working area");
        }
        user.setAreaId(null);
        return toDetail(userRepository.save(user));
    }

    @Transactional
    public UserDetailResponse promoteCollector(String userId, PromoteCollectorRequest req, String adminId) {
        parseUuid(adminId, "adminId");
        User user = loadUser(userId);
        ensureUserNotSuspended(user);

        Area area = loadActiveArea(req.getAreaId());
        Role collectorRole = loadRole(ROLE_COLLECTOR);

        user.setAreaId(area.getId());
        userRepository.save(user);

        if (!userRoleRepository.existsByUserIdAndRoleId(user.getId(), collectorRole.getId())) {
            userRoleRepository.save(UserRole.builder()
                .userId(user.getId())
                .roleId(collectorRole.getId())
                .build());
        }

        return syncUserTypeAndReturn(user);
    }

    private User loadUser(String userId) {
        return userRepository.findById(parseUuid(userId, "userId"))
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND, "User not found"));
    }

    private Role loadRole(String roleCode) {
        return roleRepository.findByCode(roleCode)
            .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND, "Role not found"));
    }

    private Area loadActiveArea(String areaId) {
        UUID areaUuid = parseUuid(areaId, "areaId");
        Area area = areaRepository.findById(areaUuid)
            .orElseThrow(() -> new AppException(ErrorCode.AREA_NOT_FOUND, "Area not found"));
        if (!area.isActive()) {
            throw new AppException(ErrorCode.AREA_INACTIVE, "Area is inactive");
        }
        return area;
    }

    private void ensureUserNotSuspended(User user) {
        if (user.getStatus() == UserStatus.SUSPENDED) {
            throw new AppException(ErrorCode.USER_SUSPENDED, "User is suspended");
        }
    }

    private boolean hasRole(UUID userId, String roleCode) {
        return userRoleRepository.existsByUserIdAndRoleCode(userId, roleCode);
    }

    private UserSummaryResponse toSummary(User user) {
        return adminMapper.toUserSummary(user, getRoleCodes(user.getId()));
    }

    private UserDetailResponse toDetail(User user) {
        return adminMapper.toUserDetail(user, getRoleCodes(user.getId()));
    }

    private List<String> getRoleCodes(UUID userId) {
        return userRoleRepository.findRoleCodesByUserId(userId);
    }

    private UserDetailResponse syncUserTypeAndReturn(User user) {
        user.setUserType(resolveUserType(user.getId()));
        User saved = userRepository.save(user);
        return toDetail(saved);
    }

    private UserType resolveUserType(UUID userId) {
        List<String> roles = getRoleCodes(userId);
        if (roles.contains(ROLE_ADMIN)) {
            return UserType.ADMIN;
        }
        if (roles.contains(ROLE_ENTERPRISE_MANAGER)) {
            return UserType.ENTERPRISE_MANAGER;
        }
        if (roles.contains(ROLE_COLLECTOR)) {
            return UserType.COLLECTOR;
        }
        if (roles.contains(ROLE_CITIZEN)) {
            return UserType.CITIZEN;
        }
        return UserType.CITIZEN;
    }

    private UserStatus parseUserStatus(String status) {
        String normalized = normalizeBlank(status);
        if (normalized == null) {
            return null;
        }
        try {
            return UserStatus.valueOf(normalized.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            throw new AppException(ErrorCode.BAD_REQUEST, "Invalid user status");
        }
    }

    private String normalizeRoleCode(String roleCode) {
        String normalized = normalizeBlank(roleCode);
        return normalized == null ? null : normalizeRoleCodeRequired(normalized);
    }

    private String normalizeRoleCodeRequired(String roleCode) {
        String normalized = normalizeBlank(roleCode);
        if (normalized == null) {
            throw new AppException(ErrorCode.BAD_REQUEST, "roleCode is required");
        }
        String upper = normalized.toUpperCase(Locale.ROOT);
        return upper.startsWith("ROLE_") ? upper : "ROLE_" + upper;
    }

    private String normalizeBlank(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }

    private UUID parseUuid(String value, String field) {
        try {
            return UUID.fromString(value);
        } catch (Exception ex) {
            throw new AppException(ErrorCode.BAD_REQUEST, "Invalid UUID for " + field);
        }
    }
}
