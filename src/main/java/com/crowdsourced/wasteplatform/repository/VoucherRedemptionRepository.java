package com.crowdsourced.wasteplatform.repository;

import com.crowdsourced.wasteplatform.entity.VoucherRedemption;
import com.crowdsourced.wasteplatform.repository.projection.CitizenRedeemedVoucherProjection;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VoucherRedemptionRepository extends JpaRepository<VoucherRedemption, UUID> {

    @EntityGraph(attributePaths = "voucher")
    Page<VoucherRedemption> findByUserIdOrderByRedeemedAtDesc(UUID userId, Pageable pageable);

    boolean existsByRedeemCode(String redeemCode);

    long countByUserId(UUID userId);

    @Query("""
        select
            vr.voucherId as voucherId,
            vr.voucher.title as voucherTitle,
            vr.redeemCode as redeemCode,
            vr.redeemedAt as redeemedAt,
            vr.status as status
        from VoucherRedemption vr
        where vr.userId = :userId
        order by vr.redeemedAt desc
        """)
    List<CitizenRedeemedVoucherProjection> findRedeemedVoucherItems(@Param("userId") UUID userId);
}
