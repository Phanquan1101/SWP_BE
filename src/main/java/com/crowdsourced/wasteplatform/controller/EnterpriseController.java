package com.crowdsourced.wasteplatform.controller;

import com.crowdsourced.wasteplatform.dto.waste_report.request.RejectReportRequest;
import com.crowdsourced.wasteplatform.dto.waste_report.response.WasteReportResponse;
import com.crowdsourced.wasteplatform.exception.ApiResponse;
import com.crowdsourced.wasteplatform.service.waste_report.WasteReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping
@Tag(name = "Enterprise")
public class EnterpriseController {
    private final WasteReportService wasteReportService;
    public EnterpriseController(WasteReportService wasteReportService) {
        this.wasteReportService = wasteReportService;
    }

    @GetMapping("/enterprise/reports")
    @Operation(
            summary = "View all list report"
    )
    public ResponseEntity<ApiResponse<List<WasteReportResponse>>> listWasteReport(@AuthenticationPrincipal UUID manageID){
        return ResponseEntity.ok(ApiResponse.success(wasteReportService.listByManage(manageID)));
    }

    @GetMapping("/enterprise/reports/inbox")
    @Operation(
            summary = "View list report by Area"
    )
    public ResponseEntity<ApiResponse<List<WasteReportResponse>>> findWasteReportByArea(@AuthenticationPrincipal UUID manageID, @RequestParam@Valid UUID areaID) {
        return  ResponseEntity.ok(ApiResponse.success(wasteReportService.listByArea(manageID, areaID)));
    }
    @PostMapping("/enterprise/reports/{id}/accept")
    @Operation(
            summary = "Accept reports by enterprise"
    )
    public ResponseEntity<ApiResponse<WasteReportResponse>> acceptWasteReport(@AuthenticationPrincipal UUID manageID, @PathVariable UUID id) {
        WasteReportResponse response = wasteReportService.accept(manageID,id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/enterprise/reports/{id}/reject")
    @Operation(
            summary = "Reject reports by enterprise"
    )
    public ResponseEntity<ApiResponse<WasteReportResponse>> rejectWasteReport(@AuthenticationPrincipal UUID manageID, @PathVariable UUID id, @RequestBody@Valid RejectReportRequest rejectReportRequest) {
        WasteReportResponse response = wasteReportService.reject(manageID,id,rejectReportRequest);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}