package com.crowdsourced.wasteplatform.controller.citizen;

import com.crowdsourced.wasteplatform.dto.common.PageResponse;
import com.crowdsourced.wasteplatform.dto.common.request.CancelRequest;
import com.crowdsourced.wasteplatform.dto.report.request.AddReportImagesRequest;
import com.crowdsourced.wasteplatform.dto.report.request.CreateWasteReportRequest;
import com.crowdsourced.wasteplatform.dto.report.response.WasteReportResponse;
import com.crowdsourced.wasteplatform.exception.ApiResponse;
import com.crowdsourced.wasteplatform.service.report.CitizenWasteReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/citizen/reports")
@Tag(name = "Citizen - Waste Reports", description = "Citizen CRUD bao cao rac")
public class CitizenWasteReportController {

    private final CitizenWasteReportService service;

    public CitizenWasteReportController(CitizenWasteReportService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Citizen tao bao cao rac")
    public ApiResponse<WasteReportResponse> create(@Valid @RequestBody CreateWasteReportRequest request) {
        String userId = currentUserId();
        return ApiResponse.success(service.createReport(request, userId));
    }

    @GetMapping
    @Operation(summary = "Citizen xem danh sach bao cao cua minh")
    public ApiResponse<PageResponse<WasteReportResponse>> list(@ParameterObject Pageable pageable) {
        String userId = currentUserId();
        return ApiResponse.success(service.getMyReports(userId, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Citizen xem chi tiet bao cao cua minh")
    public ApiResponse<WasteReportResponse> detail(@PathVariable String id) {
        String userId = currentUserId();
        return ApiResponse.success(service.getMyReportDetail(id, userId));
    }

    @PostMapping("/{id}/cancel")
    @Operation(
        summary = "Citizen huy bao cao",
        description = "Citizen chi duoc huy bao cao khi trang thai la PENDING/ACCEPTED va chua co assignment active."
    )
    public ApiResponse<WasteReportResponse> cancel(@PathVariable String id,
                                                   @Valid @RequestBody CancelRequest request) {
        String userId = currentUserId();
        return ApiResponse.success(service.cancelMyReport(id, userId, request));
    }

    @PostMapping("/{id}/images")
    @Operation(summary = "Citizen bo sung anh cho bao cao PENDING")
    public ApiResponse<WasteReportResponse> addImages(@PathVariable String id,
                                                      @Valid @RequestBody AddReportImagesRequest request) {
        String userId = currentUserId();
        return ApiResponse.success(service.addReportImages(id, userId, request));
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
