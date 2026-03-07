package com.crowdsourced.wasteplatform.controller;

import com.crowdsourced.wasteplatform.dto.reward_rule.request.UpsertRewardRuleRequest;
import com.crowdsourced.wasteplatform.dto.reward_rule.response.RewardRuleResponse;
import com.crowdsourced.wasteplatform.exception.ApiResponse;
import com.crowdsourced.wasteplatform.service.reward.RewardRuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Tag(name = "Rewards")
public class RewardRuleController {

    private final RewardRuleService rewardRuleService;

    public RewardRuleController(RewardRuleService rewardRuleService) {
        this.rewardRuleService = rewardRuleService;
    }

    @GetMapping("/admin/reward-rules")
    @Operation(summary = "List reward rules")
    public ApiResponse<List<RewardRuleResponse>> getAll() {
        return ApiResponse.success(rewardRuleService.getAll());
    }

    @GetMapping("/enterprise/reward-rules")
    @Operation(summary = "List reward rules (enterprise manager)")
    public ApiResponse<List<RewardRuleResponse>> getAllForEnterprise() {
        return ApiResponse.success(rewardRuleService.getAll());
    }

    @PutMapping("/admin/reward-rules/{wasteCategoryId}")
    @Operation(summary = "Upsert reward rule by waste category")
    public ApiResponse<RewardRuleResponse> upsert(@PathVariable UUID wasteCategoryId,
                                                  @Valid @RequestBody UpsertRewardRuleRequest request) {
        return ApiResponse.success(rewardRuleService.upsert(wasteCategoryId, request));
    }

    @PutMapping("/enterprise/reward-rules/{wasteCategoryId}")
    @Operation(summary = "Upsert reward rule by waste category (enterprise manager)")
    public ApiResponse<RewardRuleResponse> upsertForEnterprise(@PathVariable UUID wasteCategoryId,
                                                               @Valid @RequestBody UpsertRewardRuleRequest request) {
        return ApiResponse.success(rewardRuleService.upsert(wasteCategoryId, request));
    }

    @PatchMapping("/admin/reward-rules/{id}/toggle")
    @Operation(summary = "Toggle reward rule active flag")
    public ApiResponse<RewardRuleResponse> toggle(@PathVariable UUID id) {
        return ApiResponse.success(rewardRuleService.toggle(id));
    }

    @PatchMapping("/enterprise/reward-rules/{id}/toggle")
    @Operation(summary = "Toggle reward rule active flag (enterprise manager)")
    public ApiResponse<RewardRuleResponse> toggleForEnterprise(@PathVariable UUID id) {
        return ApiResponse.success(rewardRuleService.toggle(id));
    }
}
