package com.crowdsourced.wasteplatform.service.statistics;

import java.time.Instant;

public record StatisticsRangeWindow(StatisticsRange range, Instant start, Instant end) {
}
