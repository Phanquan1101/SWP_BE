package com.crowdsourced.wasteplatform.service.auth;

import com.crowdsourced.wasteplatform.dto.auth.request.LoginRequest;
import com.crowdsourced.wasteplatform.dto.auth.request.RefreshTokenRequest;
import com.crowdsourced.wasteplatform.dto.auth.response.LoginResponse;
import com.crowdsourced.wasteplatform.dto.auth.response.TokenResponse;
import com.crowdsourced.wasteplatform.dto.auth.response.UserProfileResponse;
import com.crowdsourced.wasteplatform.entity.User;
import com.crowdsourced.wasteplatform.entity.UserStatus;
import com.crowdsourced.wasteplatform.exception.AppException;
import com.crowdsourced.wasteplatform.exception.ErrorCode;
import com.crowdsourced.wasteplatform.repository.UserRepository;
import com.crowdsourced.wasteplatform.repository.UserRoleRepository;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticateService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public LoginResponse login(LoginRequest request) {
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
}
