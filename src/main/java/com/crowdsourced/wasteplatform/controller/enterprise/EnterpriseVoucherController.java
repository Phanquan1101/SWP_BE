package com.crowdsourced.wasteplatform.controller.enterprise;

import com.crowdsourced.wasteplatform.dto.common.PageResponse;
import com.crowdsourced.wasteplatform.dto.voucher.request.CreateVoucherRequest;
import com.crowdsourced.wasteplatform.dto.voucher.request.UpdateVoucherRequest;
import com.crowdsourced.wasteplatform.dto.voucher.request.UpdateVoucherStockRequest;
import com.crowdsourced.wasteplatform.dto.voucher.response.VoucherResponse;
import com.crowdsourced.wasteplatform.exception.ApiResponse;
import com.crowdsourced.wasteplatform.service.voucher.EnterpriseVoucherService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enterprise/vouchers")
@Tag(name = "Enterprise - Vouchers")
public class EnterpriseVoucherController {

    private final EnterpriseVoucherService enterpriseVoucherService;

    public EnterpriseVoucherController(EnterpriseVoucherService enterpriseVoucherService) {
        this.enterpriseVoucherService = enterpriseVoucherService;
    }

    @GetMapping
    @Operation(summary = "Enterprise list vouchers with paging")
    public ApiResponse<PageResponse<VoucherResponse>> list(
        @RequestParam(required = false) String keyword,
        @ParameterObject Pageable pageable
    ) {
        return ApiResponse.success(enterpriseVoucherService.getEnterpriseVouchers(keyword, pageable));
    }

    @PostMapping
    @Operation(summary = "Enterprise create voucher")
    public ApiResponse<VoucherResponse> create(@Valid @RequestBody CreateVoucherRequest request) {
        return ApiResponse.success(enterpriseVoucherService.createVoucher(request, currentUserId()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Enterprise get voucher detail")
    public ApiResponse<VoucherResponse> detail(@PathVariable String id) {
        return ApiResponse.success(enterpriseVoucherService.getVoucherDetail(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Enterprise update voucher")
    public ApiResponse<VoucherResponse> update(
        @PathVariable String id,
        @Valid @RequestBody UpdateVoucherRequest request
    ) {
        return ApiResponse.success(enterpriseVoucherService.updateVoucher(id, request, currentUserId()));
    }

    @PatchMapping("/{id}/toggle")
    @Operation(summary = "Enterprise toggle voucher active flag")
    public ApiResponse<VoucherResponse> toggle(
        @PathVariable String id,
        @RequestParam boolean active
    ) {
        return ApiResponse.success(enterpriseVoucherService.toggleVoucher(id, active, currentUserId()));
    }

    @PatchMapping("/{id}/stock")
    @Operation(summary = "Enterprise update voucher stock")
    public ApiResponse<VoucherResponse> updateStock(
        @PathVariable String id,
        @Valid @RequestBody UpdateVoucherStockRequest request
    ) {
        return ApiResponse.success(enterpriseVoucherService.updateStock(id, request.getStock(), currentUserId()));
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
