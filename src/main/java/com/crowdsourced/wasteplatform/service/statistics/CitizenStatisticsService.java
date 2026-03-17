package com.crowdsourced.wasteplatform.service.statistics;

import com.crowdsourced.wasteplatform.dto.statistics.citizen.CitizenRedeemedVoucherItem;
import com.crowdsourced.wasteplatform.dto.statistics.citizen.CitizenStatisticsOverviewResponse;
import com.crowdsourced.wasteplatform.exception.AppException;
import com.crowdsourced.wasteplatform.exception.ErrorCode;
import com.crowdsourced.wasteplatform.repository.VoucherRedemptionRepository;
import com.crowdsourced.wasteplatform.repository.WasteReportRepository;
import java.time.Clock;
import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CitizenStatisticsService {

    private final WasteReportRepository wasteReportRepository;
    private final VoucherRedemptionRepository voucherRedemptionRepository;
    private final Clock clock;

    public CitizenStatisticsService(WasteReportRepository wasteReportRepository,
                                    VoucherRedemptionRepository voucherRedemptionRepository,
                                    Clock clock) {
        this.wasteReportRepository = wasteReportRepository;
        this.voucherRedemptionRepository = voucherRedemptionRepository;
        this.clock = clock;
    }

    /**
     * Thong ke cho Citizen theo thang:
     * - so report da gui trong thang
     * - tong so lan doi voucher
     * - danh sach voucher da doi.
     * Toan bo aggregate count duoc tinh truc tiep tren DB.
     */
    @Transactional(readOnly = true)
    public CitizenStatisticsOverviewResponse getOverview(String citizenId, YearMonth month) {
        UUID citizenUuid = parseUuid(citizenId, "citizenId");
        YearMonth resolvedMonth = month != null ? month : YearMonth.from(clock.instant().atZone(ZoneOffset.UTC));
        Instant monthStart = resolvedMonth.atDay(1).atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant monthEnd = resolvedMonth.plusMonths(1).atDay(1).atStartOfDay(ZoneOffset.UTC).toInstant().minusNanos(1);

        long reportsSentThisMonth = wasteReportRepository.countByCitizenIdAndCreatedAtBetween(citizenUuid, monthStart, monthEnd);
        long totalVoucherRedemptions = voucherRedemptionRepository.countByUserId(citizenUuid);
        List<CitizenRedeemedVoucherItem> redeemedVouchers = voucherRedemptionRepository.findRedeemedVoucherItems(citizenUuid)
            .stream()
            .map(row -> CitizenRedeemedVoucherItem.builder()
                .voucherId(UUID.fromString(row.getVoucherId()))
                .voucherTitle(row.getVoucherTitle())
                .redeemCode(row.getRedeemCode())
                .redeemedAt(row.getRedeemedAt())
                .status(row.getStatus())
                .build())
            .toList();

        return CitizenStatisticsOverviewResponse.builder()
            .reportsSentThisMonth(reportsSentThisMonth)
            .totalVoucherRedemptions(totalVoucherRedemptions)
            .redeemedVouchers(redeemedVouchers)
            .build();
    }

    private UUID parseUuid(String value, String field) {
        try {
            return UUID.fromString(value);
        } catch (Exception ex) {
            throw new AppException(ErrorCode.BAD_REQUEST, "Invalid UUID for " + field);
        }
    }
}
