package com.crowdsourced.wasteplatform.controller.enterprise;

import com.crowdsourced.wasteplatform.dto.common.PageResponse;
import com.crowdsourced.wasteplatform.dto.enterprise.request.AssignCollectorRequest;
import com.crowdsourced.wasteplatform.dto.enterprise.request.RejectReportRequest;
import com.crowdsourced.wasteplatform.dto.enterprise.response.InboxReportItemResponse;
import com.crowdsourced.wasteplatform.dto.report.response.WasteReportResponse;
import com.crowdsourced.wasteplatform.exception.ApiResponse;
import com.crowdsourced.wasteplatform.service.enterprise.DispatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enterprise/reports")
@Tag(name = "Enterprise - Dispatch", description = "Enterprise manager dispatch flow")
public class DispatchController {

    private final DispatchService dispatchService;

    public DispatchController(DispatchService dispatchService) {
        this.dispatchService = dispatchService;
    }

    @GetMapping("/inbox")
    @Operation(summary = "Get pending/filtered reports in dispatch inbox")
    public ApiResponse<PageResponse<InboxReportItemResponse>> getInbox(
        @RequestParam(value = "areaId", required = false) String areaId,
        @RequestParam(value = "status", required = false) String status,
        @ParameterObject Pageable pageable
    ) {
        return ApiResponse.success(dispatchService.getInbox(areaId, status, pageable));
    }

    @PostMapping("/{id}/accept")
    @Operation(summary = "Accept report for dispatch")
    public ApiResponse<WasteReportResponse> accept(@PathVariable String id) {
        return ApiResponse.success(dispatchService.acceptReport(id, currentUserId()));
    }

    @PostMapping("/{id}/reject")
    @Operation(summary = "Reject report with reason")
    public ApiResponse<WasteReportResponse> reject(@PathVariable String id,
                                                   @Valid @RequestBody RejectReportRequest request) {
        return ApiResponse.success(dispatchService.rejectReport(id, currentUserId(), request.getReason()));
    }

    @PostMapping("/{id}/assign")
    @Operation(summary = "Assign collector to accepted report")
    public ApiResponse<WasteReportResponse> assign(@PathVariable String id,
                                                   @Valid @RequestBody AssignCollectorRequest request) {
        return ApiResponse.success(dispatchService.assignCollector(id, currentUserId(), request.getCollectorId()));
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
