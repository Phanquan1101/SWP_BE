package com.crowdsourced.wasteplatform.repository;

import com.crowdsourced.wasteplatform.entity.RewardRule;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RewardRuleRepository extends JpaRepository<RewardRule, UUID> {

    Optional<RewardRule> findByWasteCategoryId(UUID wasteCategoryId);

    @Query("""
        select rr from RewardRule rr
        where rr.wasteCategoryId = :wasteCategoryId
          and rr.active = true
          and rr.effectiveFrom <= :now
          and (rr.effectiveTo is null or rr.effectiveTo >= :now)
        order by rr.priority asc, rr.effectiveFrom desc
        """)
    List<RewardRule> findApplicableRules(@Param("wasteCategoryId") UUID wasteCategoryId, @Param("now") Instant now);
}
