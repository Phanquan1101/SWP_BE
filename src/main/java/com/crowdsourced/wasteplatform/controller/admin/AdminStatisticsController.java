package com.crowdsourced.wasteplatform.controller.admin;

import com.crowdsourced.wasteplatform.dto.statistics.admin.AdminCollectorsByAreaItem;
import com.crowdsourced.wasteplatform.dto.statistics.admin.AdminReportsByMonthItem;
import com.crowdsourced.wasteplatform.dto.statistics.admin.AdminStatisticsOverviewResponse;
import com.crowdsourced.wasteplatform.exception.ApiResponse;
import com.crowdsourced.wasteplatform.service.statistics.AdminStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.Clock;
import java.time.ZoneOffset;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/statistics")
@Tag(name = "Admin - Statistics")
public class AdminStatisticsController {

    private final AdminStatisticsService adminStatisticsService;
    private final Clock clock;

    public AdminStatisticsController(AdminStatisticsService adminStatisticsService, Clock clock) {
        this.adminStatisticsService = adminStatisticsService;
        this.clock = clock;
    }

    /**
     * Dashboard tong quan cho Admin theo range.
     */
    @GetMapping("/overview")
    @Operation(summary = "Thong ke tong quan admin")
    public ApiResponse<AdminStatisticsOverviewResponse> getOverview(
        @RequestParam(defaultValue = "MONTH") String range
    ) {
        return ApiResponse.success(adminStatisticsService.getOverview(range));
    }

    /**
     * Thong ke so collector theo tung working area.
     */
    @GetMapping("/collectors-by-area")
    @Operation(summary = "Thong ke collector theo khu vuc lam viec")
    public ApiResponse<List<AdminCollectorsByAreaItem>> getCollectorsByArea() {
        return ApiResponse.success(adminStatisticsService.getCollectorsByArea());
    }

    @GetMapping("/reports-by-month")
    @Operation(summary = "Thong ke so report theo thang trong nam")
    public ApiResponse<List<AdminReportsByMonthItem>> getReportsByMonth(
        @RequestParam(required = false) Integer year
    ) {
        int resolvedYear = year != null ? year : clock.instant().atZone(ZoneOffset.UTC).getYear();
        return ApiResponse.success(adminStatisticsService.getReportsByMonth(resolvedYear));
    }
}
