package com.crowdsourced.wasteplatform.controller;

import com.crowdsourced.wasteplatform.dto.admin.user.request.AssignRoleRequest;
import com.crowdsourced.wasteplatform.dto.admin.user.request.PromoteCollectorRequest;
import com.crowdsourced.wasteplatform.dto.admin.user.request.SetWorkingAreaRequest;
import com.crowdsourced.wasteplatform.dto.admin.user.request.UpdateUserStatusRequest;
import com.crowdsourced.wasteplatform.dto.admin.user.response.UserDetailResponse;
import com.crowdsourced.wasteplatform.dto.admin.user.response.UserSummaryResponse;
import com.crowdsourced.wasteplatform.dto.common.PageResponse;
import com.crowdsourced.wasteplatform.exception.ApiResponse;
import com.crowdsourced.wasteplatform.service.admin.AdminUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/users")
@Tag(name = "Admin - Users")
public class AdminUserController {

    private final AdminUserService adminUserService;

    public AdminUserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @GetMapping
    @Operation(summary = "Search users")
    public ApiResponse<PageResponse<UserSummaryResponse>> searchUsers(
        @RequestParam(required = false) String q,
        @RequestParam(name = "role", required = false) String roleCode,
        @RequestParam(required = false) String status,
        @ParameterObject Pageable pageable
    ) {
        return ApiResponse.success(adminUserService.searchUsers(q, roleCode, status, pageable));
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get user detail")
    public ApiResponse<UserDetailResponse> getUser(@PathVariable String userId) {
        return ApiResponse.success(adminUserService.getUser(userId));
    }

    @PatchMapping("/{userId}/status")
    @Operation(summary = "Update user status")
    public ApiResponse<UserDetailResponse> updateStatus(
        @PathVariable String userId,
        @Valid @RequestBody UpdateUserStatusRequest request
    ) {
        return ApiResponse.success(adminUserService.updateStatus(userId, request, currentUserId()));
    }

    @PostMapping("/{userId}/roles")
    @Operation(summary = "Assign role to user")
    public ApiResponse<UserDetailResponse> assignRole(
        @PathVariable String userId,
        @Valid @RequestBody AssignRoleRequest request
    ) {
        return ApiResponse.success(adminUserService.assignRole(userId, request.getRoleCode(), currentUserId()));
    }

    @DeleteMapping("/{userId}/roles/{roleCode}")
    @Operation(summary = "Remove role from user")
    public ApiResponse<UserDetailResponse> removeRole(@PathVariable String userId, @PathVariable String roleCode) {
        return ApiResponse.success(adminUserService.removeRole(userId, roleCode, currentUserId()));
    }

    @PutMapping("/{userId}/working-area")
    @Operation(summary = "Set working area for collector")
    public ApiResponse<UserDetailResponse> setWorkingArea(
        @PathVariable String userId,
        @Valid @RequestBody SetWorkingAreaRequest request
    ) {
        return ApiResponse.success(adminUserService.setWorkingArea(userId, request.getAreaId(), currentUserId()));
    }

    @DeleteMapping("/{userId}/working-area")
    @Operation(summary = "Clear working area")
    public ApiResponse<UserDetailResponse> clearWorkingArea(@PathVariable String userId) {
        return ApiResponse.success(adminUserService.clearWorkingArea(userId, currentUserId()));
    }

    @PostMapping("/{userId}/promote-collector")
    @Operation(summary = "Promote user to collector and set working area atomically")
    public ApiResponse<UserDetailResponse> promoteCollector(
        @PathVariable String userId,
        @Valid @RequestBody PromoteCollectorRequest request
    ) {
        return ApiResponse.success(adminUserService.promoteCollector(userId, request, currentUserId()));
    }

    private String currentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof UUID uuid) {
            return uuid.toString();
        }
        return String.valueOf(principal);
    }
}
