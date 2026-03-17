package com.crowdsourced.wasteplatform.controller.citizen;

import com.crowdsourced.wasteplatform.dto.statistics.citizen.CitizenStatisticsOverviewResponse;
import com.crowdsourced.wasteplatform.exception.ApiResponse;
import com.crowdsourced.wasteplatform.exception.AppException;
import com.crowdsourced.wasteplatform.exception.ErrorCode;
import com.crowdsourced.wasteplatform.service.statistics.CitizenStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.YearMonth;
import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/citizen/statistics")
@Tag(name = "Citizen - Statistics")
public class CitizenStatisticsController {

    private final CitizenStatisticsService citizenStatisticsService;

    public CitizenStatisticsController(CitizenStatisticsService citizenStatisticsService) {
        this.citizenStatisticsService = citizenStatisticsService;
    }

    /**
     * Citizen xem thong ke ca nhan theo thang.
     */
    @GetMapping("/overview")
    @Operation(summary = "Thong ke tong quan citizen")
    public ApiResponse<CitizenStatisticsOverviewResponse> getOverview(
        @RequestParam(required = false) String month
    ) {
        return ApiResponse.success(citizenStatisticsService.getOverview(currentUserId(), parseMonth(month)));
    }

    private YearMonth parseMonth(String month) {
        if (month == null || month.isBlank()) {
            return null;
        }
        try {
            return YearMonth.parse(month);
        } catch (Exception ex) {
            throw new AppException(ErrorCode.BAD_REQUEST, "Month must be in format yyyy-MM");
        }
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
