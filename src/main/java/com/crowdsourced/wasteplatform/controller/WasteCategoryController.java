package com.crowdsourced.wasteplatform.controller;

import com.crowdsourced.wasteplatform.dto.waste_category.request.CreateWasteCategoryRequest;
import com.crowdsourced.wasteplatform.dto.waste_category.request.UpdateWasteCategoryRequest;
import com.crowdsourced.wasteplatform.dto.waste_category.response.WasteCategoryResponse;
import com.crowdsourced.wasteplatform.exception.ApiResponse;
import com.crowdsourced.wasteplatform.service.waste_category.WasteCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Tag(name = "Admin")
public class WasteCategoryController {

    private final WasteCategoryService service;

    public WasteCategoryController(WasteCategoryService service) {
        this.service = service;
    }

    @Operation(summary = "List waste categories")
    @GetMapping("/waste-categories")
    public ResponseEntity<ApiResponse<List<WasteCategoryResponse>>> list(
        @Parameter(description = "Include inactive categories (admin only)")
        @RequestParam(name = "includeInactive", required = false, defaultValue = "false") boolean includeInactive
    ) {
        List<WasteCategoryResponse> data = service.list(includeInactive);
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @Operation(summary = "Create waste category")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Created",
            content = @Content(schema = @Schema(implementation = WasteCategoryResponse.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Code exists", content = @Content)
    })
    @PostMapping("/admin/waste-categories")
    public ResponseEntity<ApiResponse<WasteCategoryResponse>> create(@Valid @RequestBody CreateWasteCategoryRequest request) {
        WasteCategoryResponse response = service.create(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "Update waste category")
    @PutMapping("/admin/waste-categories/{id}")
    public ResponseEntity<ApiResponse<WasteCategoryResponse>> update(
        @PathVariable("id") UUID id,
        @Valid @RequestBody UpdateWasteCategoryRequest request
    ) {
        WasteCategoryResponse response = service.update(id, request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "Deactivate waste category")
    @PatchMapping("/admin/waste-categories/{id}/deactivate")
    public ResponseEntity<ApiResponse<Void>> deactivate(@PathVariable("id") UUID id) {
        service.deactivate(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @Operation(summary = "Activate waste category")
    @PatchMapping("/admin/waste-categories/{id}/activate")
    public ResponseEntity<ApiResponse<Void>> activate(@PathVariable("id") UUID id) {
        service.activate(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
