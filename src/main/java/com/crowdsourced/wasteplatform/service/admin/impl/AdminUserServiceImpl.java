package com.crowdsourced.wasteplatform.service.admin.impl;

import com.crowdsourced.wasteplatform.dto.admin.request.UpsertUserRequest;
import com.crowdsourced.wasteplatform.dto.admin.response.UserAdminResponse;
import com.crowdsourced.wasteplatform.dto.auth.request.LoginRequest;
import com.crowdsourced.wasteplatform.dto.auth.request.RegisterRequest;
import com.crowdsourced.wasteplatform.dto.auth.response.LoginResponse;
import com.crowdsourced.wasteplatform.dto.auth.response.UserProfileResponse;
import com.crowdsourced.wasteplatform.entity.Area;
import com.crowdsourced.wasteplatform.entity.Enterprise;
import com.crowdsourced.wasteplatform.entity.Role;
import com.crowdsourced.wasteplatform.entity.User;
import com.crowdsourced.wasteplatform.entity.UserRole;
import com.crowdsourced.wasteplatform.entity.UserStatus;
import com.crowdsourced.wasteplatform.entity.UserType;
import com.crowdsourced.wasteplatform.exception.AppException;
import com.crowdsourced.wasteplatform.exception.ErrorCode;
import com.crowdsourced.wasteplatform.exception.PageResponse;
import com.crowdsourced.wasteplatform.mapper.PageMapper;
import com.crowdsourced.wasteplatform.mapper.UserMapper;
import com.crowdsourced.wasteplatform.repository.AreaRepository;
import com.crowdsourced.wasteplatform.repository.EnterpriseRepository;
import com.crowdsourced.wasteplatform.repository.RoleRepository;
import com.crowdsourced.wasteplatform.repository.UserRepository;
import com.crowdsourced.wasteplatform.repository.UserRoleRepository;
import com.crowdsourced.wasteplatform.service.admin.AdminUserService;
import com.crowdsourced.wasteplatform.service.auth.AuthenticateService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository userRepository;
    private final AreaRepository areaRepository;
    private final EnterpriseRepository enterpriseRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticateService authenticateService;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;


    @Override
    @Transactional
    public LoginResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.VALIDATION, "Email already exists");
        }
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new AppException(ErrorCode.VALIDATION, "Phone already exists");
        }

        Area area = areaRepository.findById(authenticateService.parseUuidNullable(request.getAreaId()))
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Area not found"));

        User user = User.builder()
                .email(request.getEmail())
                .phone(request.getPhone())
                .fullName(request.getFullName())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .userType(UserType.CITIZEN)
                .status(UserStatus.ACTIVE)
                .areaId(area.getId())
                .enterpriseId(null).build();

        User savedUser = userRepository.save(user);


        Role role = roleRepository.findByCode("ROLE_CITIZEN")
                .orElseThrow(() ->
                        new AppException(ErrorCode.NOT_FOUND, "Default role ROLE_CITIZEN not found"));

        UserRole userRole = UserRole.builder()
                .userId(savedUser.getId())
                .roleId(role.getId())
                .role(role)
                .build();

        userRoleRepository.save(userRole);

        LoginRequest login = LoginRequest.builder()
                .identifier(request.getEmail())
                .password(request.getPassword()).build();
        return authenticateService.login(login);

    }

    @Override
    @Transactional
    public UserProfileResponse updatedProfile(UUID userId, UpsertUserRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "User not found"));

        if (request.getEmail() != null &&
                userRepository.existsByEmailAndIdNot(request.getEmail(), userId)) {
            throw new AppException(ErrorCode.VALIDATION, "Email already exists");
        }

        if (request.getPhone() != null &&
                userRepository.existsByPhoneAndIdNot(request.getPhone(), userId)) {
            throw new AppException(ErrorCode.VALIDATION, "Phone already exists");
        }

        userMapper.upsertUserRequest(request, user);

        if (request.getEnterpriseId() != null) {
            Enterprise enterprise = enterpriseRepository.findById(request.getEnterpriseId())
                    .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Enterprise not found"));
            user.setEnterpriseId(enterprise.getId());
        }

        if (request.getAreaId() != null) {
            Area area = areaRepository.findById(request.getAreaId())
                    .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Area not found"));
            user.setAreaId(area.getId());
        }

        if (request.getRoles() != null && !request.getRoles().isEmpty()) {

            userRoleRepository.deleteAllByUserId(user.getId());

            List<UserRole> userRoles = request.getRoles().stream()
                    .map(roleCode -> {
                        Role role = roleRepository.findByCode(roleCode)
                                .orElseThrow(() ->
                                        new AppException(ErrorCode.NOT_FOUND, "Role not found: " + roleCode));

                        return UserRole.builder()
                                .userId(user.getId())
                                .roleId(role.getId())
                                .role(role)
                                .build();
                    })
                    .toList();

            userRoleRepository.saveAll(userRoles);
        }

        User saved = userRepository.save(user);

        List<String> roles = userRoleRepository.findByUserIdWithRole(user.getId()).stream()
                .map(userRole -> userRole.getRole().getCode())
                .toList();
        return UserProfileResponse.builder()
                .id(saved.getId())
                .email(saved.getEmail())
                .fullName(saved.getFullName())
                .userType(saved.getUserType().name())
                .roles(roles)
                .build();
    }

    public PageResponse<UserAdminResponse> getUsers(
            String keyword,
            UserType userType,
            UserStatus status,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Page<User> users = userRepository.searchUsers(
                keyword,
                userType,
                status,
                pageable
        );

        Page<UserAdminResponse> mapped =
                users.map(userMapper::toAdminResponse);

        return PageMapper.toPageResponse(mapped);
    }

    @Override
    public UserAdminResponse getUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new AppException(ErrorCode.NOT_FOUND, "User not found with id: " + userId)
                );

        return userMapper.toAdminResponse(user);
    }


}
