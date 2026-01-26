package com.crowdsourced.wasteplatform.service.auth;

import com.crowdsourced.wasteplatform.entity.RefreshToken;
import com.crowdsourced.wasteplatform.exception.AppException;
import com.crowdsourced.wasteplatform.exception.ErrorCode;
import com.crowdsourced.wasteplatform.repository.RefreshTokenRepository;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.HexFormat;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;

    public void save(UUID userId, String refreshToken, Instant expiresAt) {
        RefreshToken entity = RefreshToken.builder()
            .userId(userId)
            .tokenHash(hash(refreshToken))
            .expiresAt(expiresAt)
            .revokedAt(null)
            .build();
        refreshTokenRepository.save(entity);
    }

    public UUID validate(String refreshToken) {
        if (!jwtService.validate(refreshToken)) {
            throw new AppException(ErrorCode.AUTH, "Invalid refresh token");
        }
        UUID userId = jwtService.extractUserId(refreshToken);
        String hash = hash(refreshToken);
        RefreshToken token = refreshTokenRepository
            .findByUserIdAndTokenHashAndRevokedAtIsNull(userId, hash)
            .orElseThrow(() -> new AppException(ErrorCode.AUTH, "Refresh token revoked"));
        if (token.getExpiresAt() != null && token.getExpiresAt().isBefore(Instant.now())) {
            throw new AppException(ErrorCode.AUTH, "Refresh token expired");
        }
        return userId;
    }

    public void revoke(String refreshToken) {
        UUID userId = jwtService.extractUserId(refreshToken);
        String hash = hash(refreshToken);
        RefreshToken token = refreshTokenRepository
            .findByUserIdAndTokenHashAndRevokedAtIsNull(userId, hash)
            .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Refresh token not found"));
        token.setRevokedAt(Instant.now());
        refreshTokenRepository.save(token);
    }

    private String hash(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashed = digest.digest(value.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hashed);
        } catch (Exception ex) {
            throw new AppException(ErrorCode.INTERNAL, "Hash error");
        }
    }
}
