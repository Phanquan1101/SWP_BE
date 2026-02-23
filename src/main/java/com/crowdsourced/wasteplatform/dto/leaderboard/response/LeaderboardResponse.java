package com.crowdsourced.wasteplatform.dto.leaderboard.response;

import java.time.Instant;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LeaderboardResponse {
    String areaId;
    int days;
    Instant generatedAt;
    List<LeaderboardRowResponse> items;
}
