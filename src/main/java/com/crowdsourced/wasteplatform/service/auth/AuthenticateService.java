package com.crowdsourced.wasteplatform.service.auth;

import com.crowdsourced.wasteplatform.dto.auth.request.LoginRequest;
import com.crowdsourced.wasteplatform.dto.auth.request.RefreshTokenRequest;
import com.crowdsourced.wasteplatform.dto.auth.request.RegisterRequest;
import com.crowdsourced.wasteplatform.dto.auth.response.LoginResponse;
import com.crowdsourced.wasteplatform.dto.auth.response.TokenResponse;
import com.crowdsourced.wasteplatform.dto.auth.response.UserProfileResponse;
import com.crowdsourced.wasteplatform.entity.User;
import com.crowdsourced.wasteplatform.entity.UserStatus;
import com.crowdsourced.wasteplatform.entity.UserType;
import com.crowdsourced.wasteplatform.entity.Role;
import com.crowdsourced.wasteplatform.exception.AppException;
import com.crowdsourced.wasteplatform.exception.ErrorCode;
import com.crowdsourced.wasteplatform.repository.AreaRepository;
import com.crowdsourced.wasteplatform.repository.UserRepository;
import com.crowdsourced.wasteplatform.repository.UserRoleRepository;
import com.crowdsourced.wasteplatform.repository.RoleRepository;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticateService {

    /**
     * Xử lý nghiệp vụ xác thực và phát hành token.
     * - login: xác thực email/phone + mật khẩu, chỉ chấp nhận user ACTIVE.
     * - register: Citizen tự đăng ký, gán ROLE_CITIZEN, validate area (nếu có).
     * - refresh: tái phát hành access token từ refresh còn hiệu lực và chưa revoke.
     * - logout: revoke refresh token.
     * Tất cả đều ghi nhận refresh token hash vào DB để kiểm soát revoke.
     */
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final AreaRepository areaRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public LoginResponse login(LoginRequest request) {
        // Login cho mọi actor; xác thực dựa trên email/phone và tình trạng ACTIVE
        String identifier = request.getIdentifier();
        User user = userRepository.findByEmailOrPhone(identifier, identifier)
            .orElseThrow(() -> new AppException(ErrorCode.AUTH, "Invalid credentials"));

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new AppException(ErrorCode.FORBIDDEN, "User is not active");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new AppException(ErrorCode.AUTH, "Invalid credentials");
        }

        List<String> roles = userRoleRepository.findByUserIdWithRole(user.getId()).stream()
            .map(userRole -> userRole.getRole().getCode())
            .toList();

        String accessToken = jwtService.generateAccessToken(user, roles);
        String refreshToken = jwtService.generateRefreshToken(user);

        Instant refreshExpiresAt = jwtService.extractExpiryInstant(refreshToken);
        refreshTokenService.save(user.getId(), refreshToken, refreshExpiresAt);

        TokenResponse tokenResponse = TokenResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .tokenType("Bearer")
            .expiresInSeconds(jwtService.getExpirySeconds(accessToken))
            .build();

        UserProfileResponse userProfile = UserProfileResponse.builder()
            .id(user.getId())
            .email(user.getEmail())
            .fullName(user.getFullName())
            .userType(user.getUserType().name())
            .roles(roles)
            .build();

        return LoginResponse.builder()
            .tokens(tokenResponse)
            .user(userProfile)
            .build();
    }

    public TokenResponse refresh(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        UUID userId = refreshTokenService.validate(refreshToken);
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "User not found"));

        List<String> roles = userRoleRepository.findByUserIdWithRole(user.getId()).stream()
            .map(userRole -> userRole.getRole().getCode())
            .toList();

        String accessToken = jwtService.generateAccessToken(user, roles);

        return TokenResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .tokenType("Bearer")
            .expiresInSeconds(jwtService.getExpirySeconds(accessToken))
            .build();
    }

    public void logout(String refreshToken) {

        refreshTokenService.revoke(refreshToken);
    }

    @Transactional
    public LoginResponse register(RegisterRequest request) {
        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            userRepository.findByEmail(request.getEmail()).ifPresent(u -> {
                throw new AppException(ErrorCode.CONFLICT, "Email already in use");
            });
        }
        if (request.getPhone() != null && !request.getPhone().isBlank()) {
            userRepository.findByPhone(request.getPhone()).ifPresent(u -> {
                throw new AppException(ErrorCode.CONFLICT, "Phone already in use");
            });
        }

        Role citizenRole = roleRepository.findByCode("ROLE_CITIZEN")
            .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Role ROLE_CITIZEN not found"));

        UUID areaId = parseUuidNullable(request.getAreaId());
        if (areaId != null && areaRepository.findById(areaId).isEmpty()) {
            throw new AppException(ErrorCode.NOT_FOUND, "Area not found");
        }

        User user = User.builder()
            .email(request.getEmail())
            .phone(request.getPhone())
            .passwordHash(passwordEncoder.encode(request.getPassword()))
            .fullName(request.getFullName())
            .userType(UserType.CITIZEN)
            .status(UserStatus.ACTIVE)
            .suspendedReason(null)
            .enterpriseId(null)
            .areaId(areaId)
            .build();

        User saved = userRepository.save(user);

        userRoleRepository.save(com.crowdsourced.wasteplatform.entity.UserRole.builder()
            .userId(saved.getId())
            .roleId(citizenRole.getId())
            .build());

        List<String> roles = List.of("ROLE_CITIZEN");

        String accessToken = jwtService.generateAccessToken(saved, roles);
        String refreshToken = jwtService.generateRefreshToken(saved);
        Instant refreshExpiresAt = jwtService.extractExpiryInstant(refreshToken);
        refreshTokenService.save(saved.getId(), refreshToken, refreshExpiresAt);

        TokenResponse tokenResponse = TokenResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .tokenType("Bearer")
            .expiresInSeconds(jwtService.getExpirySeconds(accessToken))
            .build();

        UserProfileResponse userProfile = UserProfileResponse.builder()
            .id(saved.getId())
            .email(saved.getEmail())
            .fullName(saved.getFullName())
            .userType(saved.getUserType().name())
            .roles(roles)
            .build();

        return LoginResponse.builder()
            .tokens(tokenResponse)
            .user(userProfile)
            .build();
    }

    private UUID parseUuidNullable(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return UUID.fromString(value);
    }
}
