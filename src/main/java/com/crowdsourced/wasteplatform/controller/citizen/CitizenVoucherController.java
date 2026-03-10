package com.crowdsourced.wasteplatform.controller.citizen;

import com.crowdsourced.wasteplatform.dto.common.PageResponse;
import com.crowdsourced.wasteplatform.dto.voucher.response.RedeemVoucherResponse;
import com.crowdsourced.wasteplatform.dto.voucher.response.VoucherRedemptionResponse;
import com.crowdsourced.wasteplatform.exception.ApiResponse;
import com.crowdsourced.wasteplatform.service.voucher.CitizenVoucherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/citizen/vouchers")
@Tag(name = "Citizen - Voucher Redemption")
public class CitizenVoucherController {

    private final CitizenVoucherService citizenVoucherService;

    public CitizenVoucherController(CitizenVoucherService citizenVoucherService) {
        this.citizenVoucherService = citizenVoucherService;
    }

    @PostMapping("/{id}/redeem")
    @Operation(summary = "Citizen redeem voucher")
    public ApiResponse<RedeemVoucherResponse> redeem(@PathVariable String id) {
        return ApiResponse.success(citizenVoucherService.redeemVoucher(id, currentUserId()));
    }

    @GetMapping("/redemptions")
    @Operation(summary = "Citizen redemption history")
    public ApiResponse<PageResponse<VoucherRedemptionResponse>> myRedemptions(@ParameterObject Pageable pageable) {
        return ApiResponse.success(citizenVoucherService.getMyRedemptions(currentUserId(), pageable));
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
