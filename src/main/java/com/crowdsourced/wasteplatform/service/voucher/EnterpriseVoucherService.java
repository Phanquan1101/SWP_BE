package com.crowdsourced.wasteplatform.service.voucher;

import com.crowdsourced.wasteplatform.dto.common.PageResponse;
import com.crowdsourced.wasteplatform.dto.voucher.request.CreateVoucherRequest;
import com.crowdsourced.wasteplatform.dto.voucher.request.UpdateVoucherRequest;
import com.crowdsourced.wasteplatform.dto.voucher.response.VoucherResponse;
import com.crowdsourced.wasteplatform.entity.Voucher;
import com.crowdsourced.wasteplatform.entity.VoucherDisplayStatus;
import com.crowdsourced.wasteplatform.exception.AppException;
import com.crowdsourced.wasteplatform.exception.ErrorCode;
import com.crowdsourced.wasteplatform.mapper.VoucherMapper;
import com.crowdsourced.wasteplatform.repository.VoucherRepository;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service cho Enterprise Manager quan ly voucher marketplace.
 * Toan bo validate nghiep vu tao/sua/toggle/stock nam o backend de FE khong the bo qua rule.
 */
@Service
@RequiredArgsConstructor
public class EnterpriseVoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherMapper voucherMapper;

    @Transactional(readOnly = true)
    public PageResponse<VoucherResponse> getEnterpriseVouchers(String keyword, Pageable pageable) {
        String normalizedKeyword = normalizeKeyword(keyword);
        Page<VoucherResponse> mapped = voucherRepository.searchEnterpriseVouchers(normalizedKeyword, pageable)
            .map(this::toEnterpriseResponse);
        return PageResponse.from(mapped);
    }

    @Transactional
    public VoucherResponse createVoucher(CreateVoucherRequest req, String enterpriseManagerId) {
        validateTimeRange(req.getAvailableFrom(), req.getAvailableTo());
        String normalizedCode = req.getCode().trim().toUpperCase();
        if (voucherRepository.existsByCode(normalizedCode)) {
            throw new AppException(ErrorCode.VOUCHER_CODE_ALREADY_EXISTS, "Voucher code already exists");
        }

        Voucher voucher = Voucher.builder()
            .code(normalizedCode)
            .title(req.getTitle().trim())
            .description(req.getDescription())
            .pointsCost(req.getPointsCost())
            .stock(req.getStock())
            .availableFrom(req.getAvailableFrom())
            .availableTo(req.getAvailableTo())
            .active(req.getIsActive() == null || req.getIsActive())
            .imageUrl(req.getImageUrl())
            .createdBy(parseUuid(enterpriseManagerId, "enterpriseManagerId"))
            .build();

        Voucher saved = voucherRepository.save(voucher);
        return toEnterpriseResponse(saved);
    }

    @Transactional
    public VoucherResponse updateVoucher(String voucherId, UpdateVoucherRequest req, String enterpriseManagerId) {
        parseUuid(enterpriseManagerId, "enterpriseManagerId");
        validateTimeRange(req.getAvailableFrom(), req.getAvailableTo());

        Voucher voucher = loadVoucher(voucherId);
        voucher.setTitle(req.getTitle().trim());
        voucher.setDescription(req.getDescription());
        voucher.setPointsCost(req.getPointsCost());
        voucher.setStock(req.getStock());
        voucher.setAvailableFrom(req.getAvailableFrom());
        voucher.setAvailableTo(req.getAvailableTo());
        if (req.getIsActive() != null) {
            voucher.setActive(req.getIsActive());
        }
        voucher.setImageUrl(req.getImageUrl());
        Voucher saved = voucherRepository.save(voucher);
        return toEnterpriseResponse(saved);
    }

    @Transactional
    public VoucherResponse toggleVoucher(String voucherId, boolean active, String enterpriseManagerId) {
        parseUuid(enterpriseManagerId, "enterpriseManagerId");
        Voucher voucher = loadVoucher(voucherId);
        voucher.setActive(active);
        Voucher saved = voucherRepository.save(voucher);
        return toEnterpriseResponse(saved);
    }

    @Transactional
    public VoucherResponse updateStock(String voucherId, int stock, String enterpriseManagerId) {
        parseUuid(enterpriseManagerId, "enterpriseManagerId");
        if (stock < 0) {
            throw new AppException(ErrorCode.INVALID_VOUCHER_STOCK, "Stock must be greater than or equal to 0");
        }
        Voucher voucher = loadVoucher(voucherId);
        voucher.setStock(stock);
        Voucher saved = voucherRepository.save(voucher);
        return toEnterpriseResponse(saved);
    }

    @Transactional(readOnly = true)
    public VoucherResponse getVoucherDetail(String voucherId) {
        return toEnterpriseResponse(loadVoucher(voucherId));
    }

    private Voucher loadVoucher(String voucherId) {
        return voucherRepository.findById(parseUuid(voucherId, "voucherId"))
            .orElseThrow(() -> new AppException(ErrorCode.VOUCHER_NOT_FOUND, "Voucher not found"));
    }

    private VoucherResponse toEnterpriseResponse(Voucher voucher) {
        VoucherResponse base = voucherMapper.toResponse(voucher);
        return VoucherResponse.builder()
            .id(base.getId())
            .code(base.getCode())
            .title(base.getTitle())
            .description(base.getDescription())
            .pointsCost(base.getPointsCost())
            .stock(base.getStock())
            .availableFrom(base.getAvailableFrom())
            .availableTo(base.getAvailableTo())
            .active(base.isActive())
            .imageUrl(base.getImageUrl())
            .createdAt(base.getCreatedAt())
            .updatedAt(base.getUpdatedAt())
            .displayStatus(computeVoucherDisplayStatus(voucher, Instant.now()))
            .build();
    }

    /**
     * Voucher status duoc tinh dong de tranh du lieu stale khi thoi gian thay doi.
     */
    VoucherDisplayStatus computeVoucherDisplayStatus(Voucher voucher, Instant now) {
        if (!voucher.isActive()) {
            return VoucherDisplayStatus.INACTIVE;
        }
        if (voucher.getAvailableFrom() != null && now.isBefore(voucher.getAvailableFrom())) {
            return VoucherDisplayStatus.COMING_SOON;
        }
        if (voucher.getAvailableTo() != null && now.isAfter(voucher.getAvailableTo())) {
            return VoucherDisplayStatus.EXPIRED;
        }
        if (voucher.getStock() == null || voucher.getStock() <= 0) {
            return VoucherDisplayStatus.OUT_OF_STOCK;
        }
        return VoucherDisplayStatus.OPEN;
    }

    private void validateTimeRange(Instant availableFrom, Instant availableTo) {
        if (availableFrom != null && availableTo != null && availableTo.isBefore(availableFrom)) {
            throw new AppException(ErrorCode.VOUCHER_INVALID_TIME_RANGE, "availableTo must be after availableFrom");
        }
    }

    private String normalizeKeyword(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return null;
        }
        return keyword.trim();
    }

    private UUID parseUuid(String value, String field) {
        try {
            return UUID.fromString(value);
        } catch (Exception ex) {
            throw new AppException(ErrorCode.BAD_REQUEST, "Invalid UUID for " + field);
        }
    }
}
