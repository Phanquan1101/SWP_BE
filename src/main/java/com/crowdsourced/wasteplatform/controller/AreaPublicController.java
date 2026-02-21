package com.crowdsourced.wasteplatform.controller;

import com.crowdsourced.wasteplatform.dto.area.response.AreaTreeNodeResponse;
import com.crowdsourced.wasteplatform.exception.ApiResponse;
import com.crowdsourced.wasteplatform.service.area.AreaTreeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/areas")
@Tag(name = "Area")
public class AreaPublicController {

    private final AreaTreeService areaTreeService;

    public AreaPublicController(AreaTreeService areaTreeService) {
        this.areaTreeService = areaTreeService;
    }

    @GetMapping("/tree")
    @Operation(summary = "Lay cay khu vuc TP.HCM phuc vu dropdown chon dia chi")
    public ResponseEntity<ApiResponse<AreaTreeNodeResponse>> getTree() {
        return ResponseEntity.ok(ApiResponse.success(areaTreeService.getHcmAreaTree()));
    }
}
