package com.crowdsourced.wasteplatform.repository.projection;

public interface AdminReportsByMonthProjection {

    String getMonth();

    long getReportCount();
}
