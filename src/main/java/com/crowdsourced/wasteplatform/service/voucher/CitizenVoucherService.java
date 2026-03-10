package com.crowdsourced.wasteplatform.service.voucher;

import com.crowdsourced.wasteplatform.dto.common.PageResponse;
import com.crowdsourced.wasteplatform.dto.voucher.response.RedeemVoucherResponse;
import com.crowdsourced.wasteplatform.dto.voucher.response.VoucherRedemptionResponse;
import com.crowdsourced.wasteplatform.dto.voucher.response.VoucherResponse;
import com.crowdsourced.wasteplatform.entity.PointTransaction;
import com.crowdsourced.wasteplatform.entity.TxType;
import com.crowdsourced.wasteplatform.entity.User;
import com.crowdsourced.wasteplatform.entity.Voucher;
import com.crowdsourced.wasteplatform.entity.VoucherDisplayStatus;
import com.crowdsourced.wasteplatform.entity.VoucherRedemption;
import com.crowdsourced.wasteplatform.exception.AppException;
import com.crowdsourced.wasteplatform.exception.ErrorCode;
import com.crowdsourced.wasteplatform.mapper.VoucherMapper;
import com.crowdsourced.wasteplatform.mapper.VoucherRedemptionMapper;
import com.crowdsourced.wasteplatform.repository.PointTransactionRepository;
import com.crowdsourced.wasteplatform.repository.UserRepository;
import com.crowdsourced.wasteplatform.repository.VoucherRedemptionRepository;
import com.crowdsourced.wasteplatform.repository.VoucherRepository;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service cho marketplace view + redeem flow cua Citizen.
 *
 * Diem hien tai KHONG luu trong bang user; he thong tinh tu ledger point_transactions
 * de dam bao lich su giao dich co the audit va khong bi sai lech khi rollback.
 */
@Service
@RequiredArgsConstructor
public class CitizenVoucherService {

    private static final DateTimeFormatter CODE_TIME_FORMAT =
        DateTimeFormatter.ofPattern("yyMMddHHmmss", Locale.ROOT).withZone(ZoneOffset.UTC);
    private static final String REDEEM_NOTE_TEMPLATE = "Redeemed voucher %s";
    private static final String REDEEM_SUCCESS_MESSAGE = "Redeem voucher successfully";
    private static final int MAX_REDEEM_CODE_ATTEMPTS = 10;
    private static final String CODE_ALPHABET = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    private final VoucherRepository voucherRepository;
    private final VoucherRedemptionRepository voucherRedemptionRepository;
    private final PointTransactionRepository pointTransactionRepository;
    private final UserRepository userRepository;
    private final VoucherMapper voucherMapper;
    private final VoucherRedemptionMapper voucherRedemptionMapper;

    @Transactional(readOnly = true)
    public PageResponse<VoucherResponse> getPublicVouchers(String citizenIdOrNull, Pageable pageable) {
        Page<Voucher> page = voucherRepository.findByActiveTrue(pageable);
        Long userPoints = citizenIdOrNull == null ? null : getCurrentPointBalance(citizenIdOrNull);
        Page<VoucherResponse> mapped = page.map(voucher -> enrichVoucher(voucher, userPoints));
        return PageResponse.from(mapped);
    }

    @Transactional(readOnly = true)
    public VoucherResponse getVoucherDetail(String voucherId, String citizenIdOrNull) {
        Voucher voucher = loadVoucher(voucherId);
        Long userPoints = citizenIdOrNull == null ? null : getCurrentPointBalance(citizenIdOrNull);
        return enrichVoucher(voucher, userPoints);
    }

    @Transactional
    public RedeemVoucherResponse redeemVoucher(String voucherId, String citizenId) {
        UUID citizenUuid = parseUuid(citizenId, "citizenId");
        // Khoa user de ngan race condition tru diem 2 lan khi user bam redeem dong thoi.
        User user = userRepository.findByIdForUpdate(citizenUuid)
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND, "User not found"));

        // Khoa voucher de ngan stock bi tru am khi co nhieu request cung luc.
        Voucher voucher = voucherRepository.findByIdForUpdate(parseUuid(voucherId, "voucherId"))
            .orElseThrow(() -> new AppException(ErrorCode.VOUCHER_NOT_FOUND, "Voucher not found"));

        VoucherDisplayStatus status = computeVoucherDisplayStatus(voucher, Instant.now());
        validateVoucherRedeemable(status);

        long currentPoints = pointTransactionRepository.sumPointsByUserId(user.getId());
        int pointsCost = safeNonNegative(voucher.getPointsCost(), ErrorCode.VOUCHER_REDEEM_FAILED, "Voucher pointsCost invalid");
        if (currentPoints < pointsCost) {
            long missingPoints = pointsCost - currentPoints;
            throw new AppException(
                ErrorCode.VOUCHER_INSUFFICIENT_POINTS,
                "Thieu " + missingPoints + " diem de doi voucher"
            );
        }

        voucher.setStock(voucher.getStock() - 1);
        voucherRepository.save(voucher);

        String redeemCode = generateRedeemCode();
        VoucherRedemption redemption = voucherRedemptionRepository.save(VoucherRedemption.builder()
            .voucherId(voucher.getId())
            .userId(user.getId())
            .redeemCode(redeemCode)
            .status("ISSUED")
            .note(null)
            .build());

        pointTransactionRepository.save(PointTransaction.builder()
            .userId(user.getId())
            .reportId(null)
            .txType(TxType.REDEEM)
            .points(-pointsCost)
            .description(REDEEM_NOTE_TEMPLATE.formatted(voucher.getCode()))
            .build());

        long remainingPoints = currentPoints - pointsCost;
        return RedeemVoucherResponse.builder()
            .redemptionId(redemption.getId())
            .voucherId(voucher.getId())
            .voucherCode(voucher.getCode())
            .voucherTitle(voucher.getTitle())
            .redeemCode(redemption.getRedeemCode())
            .redeemedAt(redemption.getRedeemedAt())
            .remainingPoints(remainingPoints)
            .message(REDEEM_SUCCESS_MESSAGE)
            .build();
    }

    @Transactional(readOnly = true)
    public PageResponse<VoucherRedemptionResponse> getMyRedemptions(String citizenId, Pageable pageable) {
        UUID citizenUuid = parseUuid(citizenId, "citizenId");
        Page<VoucherRedemptionResponse> mapped = voucherRedemptionRepository
            .findByUserIdOrderByRedeemedAtDesc(citizenUuid, pageable)
            .map(voucherRedemptionMapper::toResponse);
        return PageResponse.from(mapped);
    }

    VoucherDisplayStatus computeVoucherDisplayStatus(Voucher voucher, Instant now) {
        // Trang thai duoc compute dong de FE luon nhan dung state thuc te theo thoi gian hien tai.
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

    private VoucherResponse enrichVoucher(Voucher voucher, Long userPoints) {
        VoucherResponse base = voucherMapper.toResponse(voucher);
        VoucherDisplayStatus status = computeVoucherDisplayStatus(voucher, Instant.now());
        Integer missingPoints = null;
        Boolean canRedeem = null;

        if (userPoints != null) {
            int cost = safeNonNegative(voucher.getPointsCost(), ErrorCode.VOUCHER_REDEEM_FAILED, "Voucher pointsCost invalid");
            long missing = Math.max(cost - userPoints, 0);
            missingPoints = (int) missing;
            canRedeem = status == VoucherDisplayStatus.OPEN && missing == 0;
        }

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
            .displayStatus(status)
            .canRedeem(canRedeem)
            .missingPoints(missingPoints)
            .userCurrentPoints(userPoints)
            .build();
    }

    private void validateVoucherRedeemable(VoucherDisplayStatus status) {
        switch (status) {
            case INACTIVE -> throw new AppException(ErrorCode.VOUCHER_NOT_ACTIVE, "Voucher is inactive");
            case COMING_SOON -> throw new AppException(ErrorCode.VOUCHER_COMING_SOON, "Voucher is coming soon");
            case EXPIRED -> throw new AppException(ErrorCode.VOUCHER_EXPIRED, "Voucher has expired");
            case OUT_OF_STOCK -> throw new AppException(ErrorCode.VOUCHER_OUT_OF_STOCK, "Voucher is out of stock");
            case OPEN -> {
                return;
            }
            default -> throw new AppException(ErrorCode.VOUCHER_REDEEM_FAILED, "Voucher cannot be redeemed");
        }
    }

    private Voucher loadVoucher(String voucherId) {
        return voucherRepository.findById(parseUuid(voucherId, "voucherId"))
            .orElseThrow(() -> new AppException(ErrorCode.VOUCHER_NOT_FOUND, "Voucher not found"));
    }

    private long getCurrentPointBalance(String citizenId) {
        UUID userId = parseUuid(citizenId, "citizenId");
        return pointTransactionRepository.sumPointsByUserId(userId);
    }

    private String generateRedeemCode() {
        for (int i = 0; i < MAX_REDEEM_CODE_ATTEMPTS; i++) {
            String candidate = "RDM-" + CODE_TIME_FORMAT.format(Instant.now()) + "-" + randomSuffix(6);
            if (!voucherRedemptionRepository.existsByRedeemCode(candidate)) {
                return candidate;
            }
        }
        throw new AppException(ErrorCode.VOUCHER_REDEEM_FAILED, "Cannot generate unique redeem code");
    }

    private String randomSuffix(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int idx = RANDOM.nextInt(CODE_ALPHABET.length());
            sb.append(CODE_ALPHABET.charAt(idx));
        }
        return sb.toString();
    }

    private int safeNonNegative(Integer value, ErrorCode code, String message) {
        if (value == null || value < 0) {
            throw new AppException(code, message);
        }
        return value;
    }

    private UUID parseUuid(String value, String field) {
        try {
            return UUID.fromString(value);
        } catch (Exception ex) {
            throw new AppException(ErrorCode.BAD_REQUEST, "Invalid UUID for " + field);
        }
    }
}
