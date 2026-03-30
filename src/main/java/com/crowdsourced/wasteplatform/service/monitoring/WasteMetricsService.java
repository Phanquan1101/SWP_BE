package com.crowdsourced.wasteplatform.service.monitoring;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
public class WasteMetricsService {

    private static final String TAG_OUTCOME = "outcome";
    private static final String OUTCOME_SUCCESS = "success";
    private static final String OUTCOME_FAILURE = "failure";

    private final MeterRegistry meterRegistry;
    private final Counter reportCreatedCounter;
    private final Counter reportDuplicateBlockedCounter;
    private final Counter rateLimitBlockedCounter;
    private final Counter voucherRedeemSuccessCounter;
    private final Counter voucherRedeemFailedCounter;
    private final Counter complaintCreatedCounter;
    private final Counter reportAssignedCounter;
    private final Timer reportCreateSuccessTimer;
    private final Timer reportCreateFailureTimer;
    private final Timer voucherRedeemSuccessTimer;
    private final Timer voucherRedeemFailureTimer;

    public WasteMetricsService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        this.reportCreatedCounter = Counter.builder("waste_report_created_total")
            .description("Total number of waste reports created successfully")
            .register(meterRegistry);
        this.reportDuplicateBlockedCounter = Counter.builder("waste_report_duplicate_blocked_total")
            .description("Total number of duplicate waste reports blocked")
            .register(meterRegistry);
        this.rateLimitBlockedCounter = Counter.builder("waste_rate_limit_blocked_total")
            .description("Total number of requests blocked by rate limit")
            .register(meterRegistry);
        this.voucherRedeemSuccessCounter = Counter.builder("waste_voucher_redeem_success_total")
            .description("Total number of successful voucher redeem operations")
            .register(meterRegistry);
        this.voucherRedeemFailedCounter = Counter.builder("waste_voucher_redeem_failed_total")
            .description("Total number of failed voucher redeem operations")
            .register(meterRegistry);
        this.complaintCreatedCounter = Counter.builder("waste_complaint_created_total")
            .description("Total number of complaints created")
            .register(meterRegistry);
        this.reportAssignedCounter = Counter.builder("waste_report_assigned_total")
            .description("Total number of reports assigned to collectors")
            .register(meterRegistry);
        this.reportCreateSuccessTimer = Timer.builder("waste_report_create_latency")
            .description("Latency of report creation flow")
            .tag(TAG_OUTCOME, OUTCOME_SUCCESS)
            .publishPercentileHistogram()
            .register(meterRegistry);
        this.reportCreateFailureTimer = Timer.builder("waste_report_create_latency")
            .description("Latency of report creation flow")
            .tag(TAG_OUTCOME, OUTCOME_FAILURE)
            .publishPercentileHistogram()
            .register(meterRegistry);
        this.voucherRedeemSuccessTimer = Timer.builder("waste_voucher_redeem_latency")
            .description("Latency of voucher redeem flow")
            .tag(TAG_OUTCOME, OUTCOME_SUCCESS)
            .publishPercentileHistogram()
            .register(meterRegistry);
        this.voucherRedeemFailureTimer = Timer.builder("waste_voucher_redeem_latency")
            .description("Latency of voucher redeem flow")
            .tag(TAG_OUTCOME, OUTCOME_FAILURE)
            .publishPercentileHistogram()
            .register(meterRegistry);
    }

    public Timer.Sample startSample() {

        return Timer.start(meterRegistry);
    }

    public void incrementReportCreatedAfterCommit() {

        runAfterCommit(reportCreatedCounter::increment);
    }

    public void incrementReportDuplicateBlocked() {

        reportDuplicateBlockedCounter.increment();
    }

    public void incrementRateLimitBlocked() {

        rateLimitBlockedCounter.increment();
    }

    public void incrementVoucherRedeemSuccessAfterCommit() {

        runAfterCommit(voucherRedeemSuccessCounter::increment);
    }

    public void incrementVoucherRedeemFailed() {

        voucherRedeemFailedCounter.increment();
    }

    public void incrementComplaintCreatedAfterCommit() {

        runAfterCommit(complaintCreatedCounter::increment);
    }

    public void incrementReportAssignedAfterCommit() {

        runAfterCommit(reportAssignedCounter::increment);
    }

    public void recordReportCreateLatency(Timer.Sample sample, boolean success) {
        sample.stop(success ? reportCreateSuccessTimer : reportCreateFailureTimer);
    }

    public void recordVoucherRedeemLatency(Timer.Sample sample, boolean success) {
        sample.stop(success ? voucherRedeemSuccessTimer : voucherRedeemFailureTimer);
    }

    private void runAfterCommit(Runnable task) {
        if (!TransactionSynchronizationManager.isActualTransactionActive()) {
            task.run();
            return;
        }
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                task.run();
            }
        });
    }
}
