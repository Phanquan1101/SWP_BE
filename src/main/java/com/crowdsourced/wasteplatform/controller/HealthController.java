package com.crowdsourced.wasteplatform.controller;

import com.crowdsourced.wasteplatform.exception.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Admin")
public class HealthController {

    @Operation(summary = "Public health check")
    @GetMapping("/health/public")
    public ResponseEntity<ApiResponse<String>> publicHealth() {
        return ResponseEntity.ok(ApiResponse.success("OK"));
    }

    @Operation(summary = "Protected health check")
    @GetMapping("/health/protected")
    public ResponseEntity<ApiResponse<String>> protectedHealth() {
        return ResponseEntity.ok(ApiResponse.success("OK"));
    }

    @Operation(summary = "Admin ping")
    @GetMapping("/admin/ping")
    public ResponseEntity<ApiResponse<String>> adminPing() {
        return ResponseEntity.ok(ApiResponse.success("PONG"));
    }
}
