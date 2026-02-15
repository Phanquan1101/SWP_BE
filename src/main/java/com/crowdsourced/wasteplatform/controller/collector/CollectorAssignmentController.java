package com.crowdsourced.wasteplatform.controller.collector;

import com.crowdsourced.wasteplatform.dto.collector.request.UpdateCollectorStatusRequest;
import com.crowdsourced.wasteplatform.dto.collector.request.UploadProofRequest;
import com.crowdsourced.wasteplatform.dto.collector.response.AssignmentResponse;
import com.crowdsourced.wasteplatform.dto.common.PageResponse;
import com.crowdsourced.wasteplatform.exception.ApiResponse;
import com.crowdsourced.wasteplatform.service.collector.CollectorAssignmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
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
@Tag(name = "Collector", description = "Collector nhận và cập nhật assignment")
public class CollectorAssignmentController {

    private final CollectorAssignmentService service;

    public CollectorAssignmentController(CollectorAssignmentService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Collector xem danh sách assignment của mình")
    public ApiResponse<PageResponse<AssignmentResponse>> list(@RequestParam(value = "status", required = false) String status,
                                                              Pageable pageable) {
        return ApiResponse.success(service.getMyAssignments(currentUserId(), status, pageable));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Collector cập nhật trạng thái assignment")
    public ApiResponse<AssignmentResponse> updateStatus(@PathVariable String id,
                                                        @Valid @RequestBody UpdateCollectorStatusRequest request) {
        return ApiResponse.success(service.updateStatus(id, currentUserId(), request));
    }

    @PostMapping("/{id}/proof")
    @Operation(summary = "Collector upload bằng chứng thu gom (sau khi COLLECTED)")
    public ApiResponse<AssignmentResponse> uploadProof(@PathVariable String id,
                                                       @Valid @RequestBody UploadProofRequest request) {
        return ApiResponse.success(service.uploadProof(id, currentUserId(), request));
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
