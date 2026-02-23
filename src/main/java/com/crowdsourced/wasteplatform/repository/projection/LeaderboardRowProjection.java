package com.crowdsourced.wasteplatform.repository.projection;

public interface LeaderboardRowProjection {
    String getUserId();
    String getFullName();
    Long getTotalPoints();
}
