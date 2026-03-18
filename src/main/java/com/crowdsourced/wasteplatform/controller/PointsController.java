package com.crowdsourced.wasteplatform.controller;

import com.crowdsourced.wasteplatform.dto.common.PageResponse;
import com.crowdsourced.wasteplatform.dto.points.response.PointBalanceResponse;
import com.crowdsourced.wasteplatform.dto.points.response.PointTransactionResponse;
import com.crowdsourced.wasteplatform.exception.ApiResponse;
import com.crowdsourced.wasteplatform.service.reward.PointsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Tag(name = "Rewards")
public class PointsController {

    private final PointsService pointsService;

    public PointsController(PointsService pointsService) {
        this.pointsService = pointsService;
    }

    @GetMapping("/citizen/points/transactions")
    @Operation(summary = "Get point transaction history for current citizen")
    public ApiResponse<PageResponse<PointTransactionResponse>> getMyTransactions(@ParameterObject Pageable pageable) {
        return ApiResponse.success(pointsService.getCitizenTransactions(currentUserId(), pageable));
    }

    @GetMapping("/citizen/points/balance")
    @Operation(summary = "Get current point balance for current citizen")
    public ApiResponse<PointBalanceResponse> getMyBalance() {
        return ApiResponse.success(pointsService.getCitizenBalance(currentUserId()));
    }

    private String currentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof UUID uuid) {
            return uuid.toString();
        }
        return String.valueOf(principal);
    }
}
