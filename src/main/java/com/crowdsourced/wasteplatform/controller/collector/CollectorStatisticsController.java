package com.crowdsourced.wasteplatform.controller.collector;

import com.crowdsourced.wasteplatform.dto.statistics.collector.CollectorStatisticsOverviewResponse;
import com.crowdsourced.wasteplatform.exception.ApiResponse;
import com.crowdsourced.wasteplatform.service.statistics.CollectorStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/collector/statistics")
@Tag(name = "Collector - Statistics")
public class CollectorStatisticsController {

    private final CollectorStatisticsService collectorStatisticsService;

    public CollectorStatisticsController(CollectorStatisticsService collectorStatisticsService) {
        this.collectorStatisticsService = collectorStatisticsService;
    }

    /**
     * Collector xem dashboard ca nhan theo range DAY/WEEK/MONTH/YEAR.
     */
    @GetMapping("/overview")
    @Operation(summary = "Thong ke tong quan collector")
    public ApiResponse<CollectorStatisticsOverviewResponse> getOverview(
        @RequestParam(defaultValue = "MONTH") String range
    ) {
        return ApiResponse.success(collectorStatisticsService.getOverview(currentUserId(), range));
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
