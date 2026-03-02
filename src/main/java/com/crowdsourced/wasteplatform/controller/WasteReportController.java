package com.crowdsourced.wasteplatform.controller;

import java.security.Principal;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crowdsourced.wasteplatform.dto.waste_report.request.CreateWasteReportRequest;
import com.crowdsourced.wasteplatform.dto.waste_report.request.RejectReportRequest;
import com.crowdsourced.wasteplatform.dto.waste_report.response.WasteReportResponse;
import com.crowdsourced.wasteplatform.exception.ApiResponse;
import com.crowdsourced.wasteplatform.service.waste_report.WasteReportService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class WasteReportController {
    private final WasteReportService wasteReportService;

    @Operation(summary = "Create report")
    @ApiResponses({ 
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Created",
            content = @Content(schema = @Schema(implementation = WasteReportResponse.class)))
    })
    @PostMapping("/admin/report/{id}")
    public ResponseEntity<ApiResponse<WasteReportResponse>> create(@PathVariable("id") UUID id, @Valid @RequestBody CreateWasteReportRequest  request) {
        WasteReportResponse response = wasteReportService.createForCitizen(id, request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "Accept report")
    @ApiResponses({ 
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Created",
            content = @Content(schema = @Schema(implementation = WasteReportResponse.class)))
    })
    @PutMapping("/collector/report/accept/{id}")
    public ResponseEntity<ApiResponse<WasteReportResponse>> accept(Principal principal, @PathVariable("id") UUID id) {
        WasteReportResponse response = wasteReportService.accept(UUID.fromString(principal.getName()), id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "Reject report")
    @ApiResponses({ 
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Created",
            content = @Content(schema = @Schema(implementation = WasteReportResponse.class)))
    })
    @PutMapping("/collector/report/reject/{id}")
    public ResponseEntity<ApiResponse<WasteReportResponse>> reject(Principal principal, @PathVariable("id") UUID id, 
        @RequestBody RejectReportRequest req) {
        WasteReportResponse response = wasteReportService.reject(UUID.fromString(principal.getName()), id, req);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
