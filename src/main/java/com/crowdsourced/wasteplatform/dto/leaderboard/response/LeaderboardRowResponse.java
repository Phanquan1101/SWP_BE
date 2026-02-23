package com.crowdsourced.wasteplatform.dto.leaderboard.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LeaderboardRowResponse {
    int rank;
    String userId;
    String fullName;
    long totalPoints;
}
