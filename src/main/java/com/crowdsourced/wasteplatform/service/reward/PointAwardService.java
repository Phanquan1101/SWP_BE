package com.crowdsourced.wasteplatform.service.reward;

import com.crowdsourced.wasteplatform.entity.PointTransaction;
import com.crowdsourced.wasteplatform.entity.ReportStatus;
import com.crowdsourced.wasteplatform.entity.RewardRule;
import com.crowdsourced.wasteplatform.entity.TxType;
import com.crowdsourced.wasteplatform.entity.WasteReport;
import com.crowdsourced.wasteplatform.exception.AppException;
import com.crowdsourced.wasteplatform.exception.ErrorCode;
import com.crowdsourced.wasteplatform.repository.PointTransactionRepository;
import com.crowdsourced.wasteplatform.repository.RewardRuleRepository;
import com.crowdsourced.wasteplatform.repository.WasteReportRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointAwardService {

    private final WasteReportRepository wasteReportRepository;
    private final RewardRuleRepository rewardRuleRepository;
    private final PointTransactionRepository pointTransactionRepository;

    @Transactional
    public void awardPointsForReport(String reportId, String actorUserId) {
        UUID reportUuid = parseUuid(reportId, "reportId");
        parseUuid(actorUserId, "actorUserId");

        WasteReport report = wasteReportRepository.findById(reportUuid)
            .orElseThrow(() -> new AppException(ErrorCode.REPORT_NOT_FOUND, "Report not found"));
        if (report.getCurrentStatus() != ReportStatus.COLLECTED) {
            throw new AppException(ErrorCode.INVALID_REPORT_STATUS, "Points can be awarded only when report is COLLECTED");
        }

        if (pointTransactionRepository.existsByReportIdAndTxType(reportUuid, TxType.EARN)) {
            return;
        }

        RewardRule rule = rewardRuleRepository.findApplicableRules(report.getWasteCategoryId(), Instant.now())
            .stream()
            .findFirst()
            .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "No active reward rule found for waste category"));

        BigDecimal weightKg = report.getActualWeightKg() != null ? report.getActualWeightKg() : report.getEstimatedWeightKg();
        BigDecimal basePointsDecimal = weightKg.multiply(rule.getPointsPerKg());
        int basePointsInt = basePointsDecimal.setScale(0, RoundingMode.FLOOR).intValue();
        int totalPoints = basePointsInt + safeInt(rule.getBonusQualityPoints()) + safeInt(rule.getBonusFastCompletePoints());
        if (totalPoints < 0) {
            totalPoints = 0;
        }

        PointTransaction tx = PointTransaction.builder()
            .userId(report.getCitizenId())
            .reportId(reportUuid)
            .txType(TxType.EARN)
            .points(totalPoints)
            .description("Earned from report " + reportUuid)
            .build();

        try {
            pointTransactionRepository.save(tx);
        } catch (DataIntegrityViolationException ex) {
            if (pointTransactionRepository.existsByReportIdAndTxType(reportUuid, TxType.EARN)) {
                return;
            }
            throw ex;
        }
    }

    private int safeInt(Integer value) {
        return value == null ? 0 : value;
    }

    private UUID parseUuid(String value, String field) {
        try {
            return UUID.fromString(value);
        } catch (Exception ex) {
            throw new AppException(ErrorCode.BAD_REQUEST, "Invalid UUID for " + field);
        }
    }
}
