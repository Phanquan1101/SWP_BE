package com.crowdsourced.wasteplatform.controller;

import com.crowdsourced.wasteplatform.exception.ApiResponse;
import com.crowdsourced.wasteplatform.dto.area.request.CreateAreaRequest;
import com.crowdsourced.wasteplatform.dto.area.request.UpdateAreaRequest;
import com.crowdsourced.wasteplatform.dto.area.response.AreaResponse;
import com.crowdsourced.wasteplatform.service.area.AreaService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Tag(name = "Admin")
public class AreaController {

    private final AreaService areaService;

    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }

    @Operation(summary = "List active areas")
    @GetMapping("/areas")
    public ResponseEntity<ApiResponse<List<AreaResponse>>> getAreas() {
        List<AreaResponse> data = areaService.getAllActive();
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @Operation(summary = "Create area")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Created",
            content = @Content(schema = @Schema(implementation = AreaResponse.class)))
    })
    @PostMapping("/admin/areas")
    public ResponseEntity<ApiResponse<AreaResponse>> createArea(@Valid @RequestBody CreateAreaRequest request) {
        AreaResponse response = areaService.create(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "Update area")
    @PutMapping("/admin/areas/{id}")
    public ResponseEntity<ApiResponse<AreaResponse>> updateArea(
        @PathVariable("id") UUID id,
        @Valid @RequestBody UpdateAreaRequest request
    ) {
        AreaResponse response = areaService.update(id, request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "Deactivate area")
    @PatchMapping("/admin/areas/{id}/deactivate")
    public ResponseEntity<ApiResponse<Void>> deactivate(@PathVariable("id") UUID id) {
        areaService.deactivate(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
