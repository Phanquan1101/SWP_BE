package com.crowdsourced.wasteplatform.controller.collector;

import com.crowdsourced.wasteplatform.dto.collector.request.UpdateCollectorStatusRequest;
import com.crowdsourced.wasteplatform.dto.collector.request.UploadProofRequest;
import com.crowdsourced.wasteplatform.dto.collector.response.AssignmentResponse;
import com.crowdsourced.wasteplatform.dto.common.PageResponse;
import com.crowdsourced.wasteplatform.dto.common.request.CancelRequest;
import com.crowdsourced.wasteplatform.dto.report.response.WasteReportResponse;
import com.crowdsourced.wasteplatform.exception.ApiResponse;
import com.crowdsourced.wasteplatform.service.collector.CollectorAssignmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/collector/assignments")
@Tag(name = "Collector", description = "Collector receives and updates assignments")
public class CollectorAssignmentController {

    private final CollectorAssignmentService service;

    public CollectorAssignmentController(CollectorAssignmentService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Collector gets own assignments")
    public ApiResponse<PageResponse<AssignmentResponse>> list(
        @RequestParam(value = "status", required = false) String status,
        @ParameterObject Pageable pageable
    ) {
        return ApiResponse.success(service.getMyAssignments(currentUserId(), status, pageable));
    }

    @GetMapping("/report/{reportId}")
    @Operation(summary = "Collector gets report detail of owned assignment")
    public ApiResponse<WasteReportResponse> getReportDetail(@PathVariable String reportId) {
        return ApiResponse.success(service.getOwnedReportDetail(reportId, currentUserId()));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Collector updates assignment status")
    public ApiResponse<AssignmentResponse> updateStatus(
        @PathVariable String id,
        @Valid @RequestBody UpdateCollectorStatusRequest request
    ) {
        return ApiResponse.success(service.updateStatus(id, currentUserId(), request));
    }

    @PostMapping("/{id}/proof")
    @Operation(summary = "Collector uploads proof after COLLECTED")
    public ApiResponse<AssignmentResponse> uploadProof(
        @PathVariable String id,
        @Valid @RequestBody UploadProofRequest request
    ) {
        return ApiResponse.success(service.uploadProof(id, currentUserId(), request));
    }

    @PostMapping("/{id}/cancel")
    @Operation(
        summary = "Collector huy assignment",
        description = "Collector chi duoc huy khi assignment dang ASSIGNED hoac ON_THE_WAY; report se quay ve ACCEPTED de enterprise dieu phoi lai."
    )
    public ApiResponse<AssignmentResponse> cancelAssignment(
        @PathVariable String id,
        @Valid @RequestBody CancelRequest request
    ) {
        return ApiResponse.success(service.cancelAssignment(id, currentUserId(), request));
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
