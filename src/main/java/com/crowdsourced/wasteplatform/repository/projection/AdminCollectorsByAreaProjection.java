package com.crowdsourced.wasteplatform.repository.projection;

public interface AdminCollectorsByAreaProjection {

    String getAreaId();

    String getAreaName();

    long getCollectorCount();
}
