package com.crowdsourced.wasteplatform.controller;

import com.crowdsourced.wasteplatform.dto.common.PageResponse;
import com.crowdsourced.wasteplatform.dto.voucher.response.VoucherResponse;
import com.crowdsourced.wasteplatform.exception.ApiResponse;
import com.crowdsourced.wasteplatform.service.voucher.CitizenVoucherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vouchers")
@Tag(name = "Vouchers")
public class VoucherController {

    private final CitizenVoucherService citizenVoucherService;

    public VoucherController(CitizenVoucherService citizenVoucherService) {
        this.citizenVoucherService = citizenVoucherService;
    }

    @GetMapping
    @Operation(summary = "Public voucher catalog (co the tinh canRedeem neu la citizen da dang nhap)")
    public ApiResponse<PageResponse<VoucherResponse>> list(@ParameterObject Pageable pageable) {
        return ApiResponse.success(citizenVoucherService.getPublicVouchers(currentCitizenIdOrNull(), pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Voucher detail with computed display status")
    public ApiResponse<VoucherResponse> detail(@PathVariable String id) {
        return ApiResponse.success(citizenVoucherService.getVoucherDetail(id, currentCitizenIdOrNull()));
    }

    private String currentCitizenIdOrNull() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
        boolean isCitizen = authentication.getAuthorities().stream()
            .anyMatch(a -> "ROLE_CITIZEN".equals(a.getAuthority()));
        if (!isCitizen) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UUID uuid) {
            return uuid.toString();
        }
        String value = String.valueOf(principal);
        return value.isBlank() || "anonymousUser".equalsIgnoreCase(value) ? null : value;
    }
}
