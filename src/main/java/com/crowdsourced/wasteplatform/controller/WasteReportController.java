package com.crowdsourced.wasteplatform.controller;

import com.crowdsourced.wasteplatform.dto.waste_report.request.CreateWasteReportRequest;
import com.crowdsourced.wasteplatform.dto.waste_report.request.UpdateWasteReportRequest;
import com.crowdsourced.wasteplatform.dto.waste_report.response.WasteReportResponse;
import com.crowdsourced.wasteplatform.entity.WasteReport;
import com.crowdsourced.wasteplatform.exception.ApiResponse;
import com.crowdsourced.wasteplatform.service.waste_report.WasteReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.coyote.Request;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping
@Tag(name = "Citizen")

public class WasteReportController {
    private final WasteReportService wasteReportService;
    public WasteReportController(WasteReportService wasteReportService) {
        this.wasteReportService = wasteReportService;
    }

    @PostMapping("/citizen/reports")
    @Operation(summary = "Create waste report")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Created",
                    content = @Content(schema = @Schema(implementation = WasteReportResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Code exists", content = @Content)
    })

    //Tạo báo cáo
    public ResponseEntity<ApiResponse<WasteReportResponse>> createWasteReport(@AuthenticationPrincipal UUID citizenID, @Valid @RequestBody CreateWasteReportRequest createWasteReportRequest) {
        WasteReportResponse response = wasteReportService.createForCitizen(citizenID,  createWasteReportRequest);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/citizen/reports")
    @Operation(
            summary = "View list waste report"
    )
    public ResponseEntity<ApiResponse<List<WasteReportResponse>>> listByCitizen(@AuthenticationPrincipal UUID citizenID) {
        List<WasteReportResponse> list = wasteReportService.listByCitizen(citizenID);
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    @GetMapping("/citizen/reports/{id}")
    @Operation(
            summary = "View an waste report"
    )
    public ResponseEntity<ApiResponse<WasteReportResponse>> viewAnWasteReport (@AuthenticationPrincipal UUID citizenID, @PathVariable UUID id) {
        WasteReportResponse response = wasteReportService.getByCitizen(citizenID, id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/citizen/reports/{reportID}")
    @Operation(
            summary = "Update waste report while PENDING status"
    )
    public ResponseEntity<ApiResponse<WasteReportResponse>> updateWasteReport (@AuthenticationPrincipal UUID citizenID, @Valid @RequestBody UpdateWasteReportRequest updateWasteReportRequest, @PathVariable UUID reportID) {
        WasteReportResponse response = wasteReportService.updateReportForCitizen(citizenID,updateWasteReportRequest,reportID);
        return ResponseEntity.ok(ApiResponse.success(response));
    }


}