package com.crowdsourced.wasteplatform.dto.reward_rule.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpsertRewardRuleRequest {

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal pointsPerKg;

    @NotNull
    @Min(0)
    private Integer bonusQualityPoints;

    @NotNull
    @Min(0)
    private Integer bonusFastCompletePoints;

    private Instant effectiveFrom;
    private Instant effectiveTo;

    @Min(0)
    private Integer priority;
}
