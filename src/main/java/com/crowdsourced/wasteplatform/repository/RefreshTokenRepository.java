package com.crowdsourced.wasteplatform.repository;

import com.crowdsourced.wasteplatform.entity.RefreshToken;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    Optional<RefreshToken> findByUserIdAndTokenHashAndRevokedAtIsNull(UUID userId, String tokenHash);
}
