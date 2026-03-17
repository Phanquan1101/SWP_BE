package com.crowdsourced.wasteplatform.service.statistics;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import org.springframework.stereotype.Component;

@Component
public class StatisticsRangeResolver {

    private final Clock clock;

    public StatisticsRangeResolver(Clock clock) {
        this.clock = clock;
    }

    public StatisticsRangeWindow resolve(String rangeRaw) {
        StatisticsRange range = StatisticsRange.from(rangeRaw);
        Instant now = clock.instant();
        ZonedDateTime nowUtc = now.atZone(ZoneOffset.UTC);

        ZonedDateTime start;
        switch (range) {
            case DAY -> start = nowUtc.toLocalDate().atStartOfDay(ZoneOffset.UTC);
            case WEEK -> start = nowUtc.toLocalDate()
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                .atStartOfDay(ZoneOffset.UTC);
            case MONTH -> start = nowUtc.withDayOfMonth(1).toLocalDate().atStartOfDay(ZoneOffset.UTC);
            case YEAR -> start = nowUtc.withDayOfYear(1).toLocalDate().atStartOfDay(ZoneOffset.UTC);
            default -> throw new IllegalStateException("Unexpected value: " + range);
        }

        return new StatisticsRangeWindow(range, start.toInstant(), now);
    }
}
