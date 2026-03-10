package com.crowdsourced.wasteplatform.repository;

import com.crowdsourced.wasteplatform.entity.VoucherRedemption;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherRedemptionRepository extends JpaRepository<VoucherRedemption, UUID> {

    @EntityGraph(attributePaths = "voucher")
    Page<VoucherRedemption> findByUserIdOrderByRedeemedAtDesc(UUID userId, Pageable pageable);

    boolean existsByRedeemCode(String redeemCode);
}
