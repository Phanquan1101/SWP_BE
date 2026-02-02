package com.crowdsourced.wasteplatform.controller;

import com.crowdsourced.wasteplatform.dto.capability.request.UpsertWasteCapabilityRequest;
import com.crowdsourced.wasteplatform.dto.capability.response.WasteCapabilityResponse;
import com.crowdsourced.wasteplatform.exception.ApiResponse;
import com.crowdsourced.wasteplatform.service.capability.WasteCapabilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Tag(name = "Admin")
public class WasteCapabilityController {

    private final WasteCapabilityService service;

    public WasteCapabilityController(WasteCapabilityService service) {
        this.service = service;
    }

    @Operation(summary = "List waste capabilities")
    @GetMapping("/admin/waste-capabilities")
    public ResponseEntity<ApiResponse<List<WasteCapabilityResponse>>> list() {
        List<WasteCapabilityResponse> data = service.getAll();
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @Operation(summary = "Upsert waste capability by waste category")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Saved",
            content = @Content(schema = @Schema(implementation = WasteCapabilityResponse.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Waste category not found", content = @Content)
    })
    @PutMapping("/admin/waste-capabilities/{wasteCategoryId}")
    public ResponseEntity<ApiResponse<WasteCapabilityResponse>> upsert(
        @PathVariable("wasteCategoryId") UUID wasteCategoryId,
        @Valid @RequestBody UpsertWasteCapabilityRequest request
    ) {
        WasteCapabilityResponse response = service.upsert(wasteCategoryId, request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "Toggle accepting flag")
    @PatchMapping("/admin/waste-capabilities/{wasteCategoryId}/toggle")
    public ResponseEntity<ApiResponse<WasteCapabilityResponse>> toggle(@PathVariable("wasteCategoryId") UUID wasteCategoryId) {
        WasteCapabilityResponse response = service.toggle(wasteCategoryId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
