package com.crowdsourced.wasteplatform.service.reward;

import com.crowdsourced.wasteplatform.dto.reward_rule.request.UpsertRewardRuleRequest;
import com.crowdsourced.wasteplatform.dto.reward_rule.response.RewardRuleResponse;
import com.crowdsourced.wasteplatform.entity.RewardRule;
import com.crowdsourced.wasteplatform.exception.AppException;
import com.crowdsourced.wasteplatform.exception.ErrorCode;
import com.crowdsourced.wasteplatform.mapper.RewardRuleMapper;
import com.crowdsourced.wasteplatform.repository.RewardRuleRepository;
import com.crowdsourced.wasteplatform.repository.WasteCategoryRepository;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RewardRuleService {

    private final RewardRuleRepository rewardRuleRepository;
    private final WasteCategoryRepository wasteCategoryRepository;
    private final RewardRuleMapper rewardRuleMapper;

    @Transactional(readOnly = true)
    public List<RewardRuleResponse> getAll() {
        return rewardRuleRepository.findAll().stream()
            .sorted(Comparator.comparing(RewardRule::getWasteCategoryId))
            .map(rewardRuleMapper::toResponse)
            .toList();
    }

    @Transactional
    public RewardRuleResponse upsert(UUID wasteCategoryId, UpsertRewardRuleRequest request) {
        wasteCategoryRepository.findById(wasteCategoryId)
            .orElseThrow(() -> new AppException(ErrorCode.WASTE_CATEGORY_NOT_FOUND, "Waste category not found"));

        RewardRule rule = rewardRuleRepository.findByWasteCategoryId(wasteCategoryId)
            .orElseGet(() -> RewardRule.builder()
                .wasteCategoryId(wasteCategoryId)
                .active(true)
                .build());

        rule.setPointsPerKg(request.getPointsPerKg());
        rule.setBonusQualityPoints(request.getBonusQualityPoints());
        rule.setBonusFastCompletePoints(request.getBonusFastCompletePoints());
        if (request.getEffectiveFrom() != null) {
            rule.setEffectiveFrom(request.getEffectiveFrom());
        }
        rule.setEffectiveTo(request.getEffectiveTo());
        if (request.getPriority() != null) {
            rule.setPriority(request.getPriority());
        }
        if (rule.getEffectiveFrom() == null) {
            rule.setEffectiveFrom(Instant.now());
        }
        if (rule.getPriority() == null) {
            rule.setPriority(100);
        }
        rule.setActive(true);

        RewardRule saved = rewardRuleRepository.save(rule);
        return rewardRuleMapper.toResponse(saved);
    }

    @Transactional
    public RewardRuleResponse toggle(UUID rewardRuleId) {
        RewardRule rule = rewardRuleRepository.findById(rewardRuleId)
            .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Reward rule not found"));
        rule.setActive(!rule.isActive());
        RewardRule saved = rewardRuleRepository.save(rule);
        return rewardRuleMapper.toResponse(saved);
    }
}
