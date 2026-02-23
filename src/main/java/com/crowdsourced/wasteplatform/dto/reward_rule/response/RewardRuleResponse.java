package com.crowdsourced.wasteplatform.dto.reward_rule.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RewardRuleResponse {
    UUID id;
    UUID wasteCategoryId;
    BigDecimal pointsPerKg;
    Integer bonusQualityPoints;
    Integer bonusFastCompletePoints;
    Instant effectiveFrom;
    Instant effectiveTo;
    Integer priority;
    boolean active;
    Instant createdAt;
    Instant updatedAt;
}
