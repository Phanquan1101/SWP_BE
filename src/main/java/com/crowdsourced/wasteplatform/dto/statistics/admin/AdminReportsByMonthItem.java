package com.crowdsourced.wasteplatform.dto.statistics.admin;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminReportsByMonthItem {

    private String month;
    private long reportCount;
}
