package com.crowdsourced.wasteplatform.controller;

import com.crowdsourced.wasteplatform.dto.leaderboard.response.LeaderboardResponse;
import com.crowdsourced.wasteplatform.exception.ApiResponse;
import com.crowdsourced.wasteplatform.service.reward.AreaLeaderboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/areas")
@Tag(name = "Area")
public class LeaderboardController {

    private final AreaLeaderboardService areaLeaderboardService;

    public LeaderboardController(AreaLeaderboardService areaLeaderboardService) {
        this.areaLeaderboardService = areaLeaderboardService;
    }

    @GetMapping("/{areaId}/leaderboard")
    @Operation(summary = "Lay bang xep hang theo khu vuc",
        description = "Tong hop diem EARN theo area cua report trong khoang ngay yeu cau")
    public ApiResponse<LeaderboardResponse> getLeaderboard(
        @PathVariable String areaId,
        @RequestParam(defaultValue = "30") @Min(1) @Max(365) int days,
        @RequestParam(defaultValue = "50") @Min(1) @Max(200) int limit
    ) {
        return ApiResponse.success(areaLeaderboardService.getAreaLeaderboard(areaId, days, limit));
    }
}
