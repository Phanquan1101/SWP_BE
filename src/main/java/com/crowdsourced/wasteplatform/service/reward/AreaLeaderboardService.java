package com.crowdsourced.wasteplatform.service.reward;

import com.crowdsourced.wasteplatform.dto.leaderboard.response.LeaderboardResponse;
import com.crowdsourced.wasteplatform.dto.leaderboard.response.LeaderboardRowResponse;
import com.crowdsourced.wasteplatform.entity.Area;
import com.crowdsourced.wasteplatform.exception.AppException;
import com.crowdsourced.wasteplatform.exception.ErrorCode;
import com.crowdsourced.wasteplatform.repository.AreaRepository;
import com.crowdsourced.wasteplatform.repository.PointTransactionRepository;
import com.crowdsourced.wasteplatform.repository.projection.LeaderboardRowProjection;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AreaLeaderboardService {

    private static final int MAX_LIMIT = 200;

    private final AreaRepository areaRepository;
    private final PointTransactionRepository pointTransactionRepository;

    @Transactional(readOnly = true)
    public LeaderboardResponse getAreaLeaderboard(String areaId, int days, int limit) {
        if (days <= 0) {
            throw new AppException(ErrorCode.BAD_REQUEST, "days must be greater than 0");
        }
        if (limit <= 0) {
            throw new AppException(ErrorCode.BAD_REQUEST, "limit must be greater than 0");
        }

        int safeLimit = Math.min(limit, MAX_LIMIT);
        UUID areaUuid = parseUuid(areaId);
        Area area = areaRepository.findById(areaUuid)
            .filter(Area::isActive)
            .orElseThrow(() -> new AppException(ErrorCode.AREA_NOT_FOUND, "Area not found or inactive"));

        List<LeaderboardRowProjection> rows = pointTransactionRepository.findAreaLeaderboard(
            area.getId().toString(),
            days,
            safeLimit
        );

        List<LeaderboardRowResponse> items = mapWithRank(rows);
        return LeaderboardResponse.builder()
            .areaId(area.getId().toString())
            .days(days)
            .generatedAt(Instant.now())
            .items(items)
            .build();
    }

    private List<LeaderboardRowResponse> mapWithRank(List<LeaderboardRowProjection> rows) {
        java.util.ArrayList<LeaderboardRowResponse> items = new java.util.ArrayList<>(rows.size());
        for (int i = 0; i < rows.size(); i++) {
            LeaderboardRowProjection row = rows.get(i);
            items.add(LeaderboardRowResponse.builder()
                .rank(i + 1)
                .userId(row.getUserId())
                .fullName(row.getFullName())
                .totalPoints(row.getTotalPoints() == null ? 0L : row.getTotalPoints())
                .build());
        }
        return items;
    }

    private UUID parseUuid(String value) {
        try {
            return UUID.fromString(value);
        } catch (Exception ex) {
            throw new AppException(ErrorCode.BAD_REQUEST, "Invalid areaId");
        }
    }
}
