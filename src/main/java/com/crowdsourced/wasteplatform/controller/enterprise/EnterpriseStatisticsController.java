package com.crowdsourced.wasteplatform.controller.enterprise;

import com.crowdsourced.wasteplatform.dto.statistics.enterprise.EnterpriseStatisticsOverviewResponse;
import com.crowdsourced.wasteplatform.exception.ApiResponse;
import com.crowdsourced.wasteplatform.service.statistics.EnterpriseStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enterprise/statistics")
@Tag(name = "Enterprise - Statistics")
public class EnterpriseStatisticsController {

    private final EnterpriseStatisticsService enterpriseStatisticsService;

    public EnterpriseStatisticsController(EnterpriseStatisticsService enterpriseStatisticsService) {
        this.enterpriseStatisticsService = enterpriseStatisticsService;
    }

    /**
     * Enterprise xem dashboard tong quan theo khoang thoi gian.
     */
    @GetMapping("/overview")
    @Operation(summary = "Thong ke tong quan enterprise")
    public ApiResponse<EnterpriseStatisticsOverviewResponse> getOverview(
        @RequestParam(defaultValue = "MONTH") String range
    ) {
        return ApiResponse.success(enterpriseStatisticsService.getOverview(range));
    }
}
