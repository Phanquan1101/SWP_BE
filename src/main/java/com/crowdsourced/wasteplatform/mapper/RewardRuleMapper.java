package com.crowdsourced.wasteplatform.mapper;

import com.crowdsourced.wasteplatform.dto.reward_rule.response.RewardRuleResponse;
import com.crowdsourced.wasteplatform.entity.RewardRule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RewardRuleMapper {
    RewardRuleResponse toResponse(RewardRule entity);
}
