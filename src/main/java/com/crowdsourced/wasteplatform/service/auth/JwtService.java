package com.crowdsourced.wasteplatform.service.auth;

import com.crowdsourced.wasteplatform.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    /**
     * Service sinh và kiểm tra JWT cho toàn bộ hệ thống.
     * Claims tối thiểu: uid (UUID user) + roles[] + typ (access/refresh).
     * Không chứa enterpriseId vì mô hình single-enterprise.
     * Actor: mọi role; được gọi bởi filter và AuthenticateService.
     */
    private final String secret;
    private final long accessTokenExpiryMinutes;
    private final long refreshTokenExpiryDays;

    public JwtService(
        @Value("${app.jwt.secret:change-me-change-me-change-me-change-me}") String secret,
        @Value("${app.jwt.access-token-exp-minutes:30}") long accessTokenExpiryMinutes,
        @Value("${app.jwt.refresh-token-exp-days:14}") long refreshTokenExpiryDays
    ) {
        this.secret = secret;
        this.accessTokenExpiryMinutes = accessTokenExpiryMinutes;
        this.refreshTokenExpiryDays = refreshTokenExpiryDays;
    }

    public String generateAccessToken(User user, List<String> roles) {
        Instant now = Instant.now();
        Instant expiry = now.plusSeconds(accessTokenExpiryMinutes * 60);
        return Jwts.builder()
            .subject(user.getId().toString())
            .claim("uid", user.getId().toString())
            .claim("roles", roles != null ? roles : Collections.emptyList())
            .claim("typ", "access")
            .issuedAt(Date.from(now))
            .expiration(Date.from(expiry))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    public String generateRefreshToken(User user) {
        Instant now = Instant.now();
        Instant expiry = now.plusSeconds(refreshTokenExpiryDays * 24 * 60 * 60);
        return Jwts.builder()
            .subject(user.getId().toString())
            .claim("uid", user.getId().toString())
            .claim("typ", "refresh")
            .issuedAt(Date.from(now))
            .expiration(Date.from(expiry))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    public boolean validate(String token) {
        try {
            Claims claims = parseClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean isAccessTokenValid(String token) {
        if (!validate(token)) {
            return false;
        }
        String type = extractTokenType(token);
        return "access".equalsIgnoreCase(type);
    }

    public UUID extractUserId(String token) {
        Claims claims = parseClaims(token);
        String value = claims.get("uid", String.class);
        if (value == null) {
            value = claims.getSubject();
        }
        return UUID.fromString(value);
    }

    public List<String> extractRoles(String token) {
        Claims claims = parseClaims(token);
        Object raw = claims.get("roles");
        if (raw instanceof List<?> list) {
            return list.stream().map(String::valueOf).toList();
        }
        return Collections.emptyList();
    }

    public long getExpirySeconds(String token) {
        Claims claims = parseClaims(token);
        long expiry = claims.getExpiration().toInstant().getEpochSecond();
        long now = Instant.now().getEpochSecond();
        return Math.max(expiry - now, 0);
    }

    public Instant extractExpiryInstant(String token) {
        Claims claims = parseClaims(token);
        return claims.getExpiration().toInstant();
    }

    private String extractTokenType(String token) {
        Claims claims = parseClaims(token);
        return claims.get("typ", String.class);
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
