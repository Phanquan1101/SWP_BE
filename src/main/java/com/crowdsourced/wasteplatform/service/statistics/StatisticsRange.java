package com.crowdsourced.wasteplatform.service.statistics;

import com.crowdsourced.wasteplatform.exception.AppException;
import com.crowdsourced.wasteplatform.exception.ErrorCode;

public enum StatisticsRange {
    DAY,
    WEEK,
    MONTH,
    YEAR;

    public static StatisticsRange from(String rawValue) {
        if (rawValue == null || rawValue.isBlank()) {
            return MONTH;
        }
        try {
            return StatisticsRange.valueOf(rawValue.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new AppException(ErrorCode.BAD_REQUEST, "Range must be one of DAY, WEEK, MONTH, YEAR");
        }
    }
}
