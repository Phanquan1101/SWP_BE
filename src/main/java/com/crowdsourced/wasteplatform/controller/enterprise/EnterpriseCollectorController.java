package com.crowdsourced.wasteplatform.controller.enterprise;

import com.crowdsourced.wasteplatform.dto.common.PageResponse;
import com.crowdsourced.wasteplatform.dto.enterprise.response.CollectorPickResponse;
import com.crowdsourced.wasteplatform.exception.ApiResponse;
import com.crowdsourced.wasteplatform.service.enterprise.EnterpriseCollectorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enterprise/collectors")
@Tag(name = "Enterprise - Collectors")
public class EnterpriseCollectorController {

    private final EnterpriseCollectorService enterpriseCollectorService;

    public EnterpriseCollectorController(EnterpriseCollectorService enterpriseCollectorService) {
        this.enterpriseCollectorService = enterpriseCollectorService;
    }

    @GetMapping
    @Operation(summary = "List active collectors by working area")
    public ApiResponse<PageResponse<CollectorPickResponse>> getCollectors(
        @RequestParam(required = false) String areaId,
        @ParameterObject Pageable pageable
    ) {
        return ApiResponse.success(enterpriseCollectorService.getCollectors(areaId, pageable));
    }
}
