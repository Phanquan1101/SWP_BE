package com.crowdsourced.wasteplatform.controller;

import com.crowdsourced.wasteplatform.dto.waste_category.response.WasteCategoryResponse;
import com.crowdsourced.wasteplatform.dto.waste_report.request.CreateWasteReportRequest;
import com.crowdsourced.wasteplatform.dto.waste_report.response.WasteReportResponse;
import com.crowdsourced.wasteplatform.exception.ApiResponse;
import com.crowdsourced.wasteplatform.service.waste_report.WasteReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Created",
                    content = @Content(schema = @Schema(implementation = WasteReportResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Code exists", content = @Content)
    })
    public ResponseEntity<ApiResponse<WasteReportResponse>> createWasteReport(@AuthenticationPrincipal UUID citizenID, @Valid @RequestBody CreateWasteReportRequest createWasteReportRequest) {
        WasteReportResponse response = wasteReportService.createForCitizen(citizenID,  createWasteReportRequest);
        return ResponseEntity.ok(ApiResponse.success(response));
    }




}