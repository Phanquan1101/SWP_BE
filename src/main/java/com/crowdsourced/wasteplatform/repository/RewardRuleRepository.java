package com.crowdsourced.wasteplatform.repository;

import com.crowdsourced.wasteplatform.entity.RewardRule;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RewardRuleRepository extends JpaRepository<RewardRule, UUID> {
}
