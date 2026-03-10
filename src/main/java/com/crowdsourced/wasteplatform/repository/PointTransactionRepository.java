package com.crowdsourced.wasteplatform.repository;

import com.crowdsourced.wasteplatform.entity.PointTransaction;
import com.crowdsourced.wasteplatform.entity.TxType;
import com.crowdsourced.wasteplatform.repository.projection.LeaderboardRowProjection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PointTransactionRepository extends JpaRepository<PointTransaction, UUID> {

    boolean existsByReportIdAndTxType(UUID reportId, TxType txType);

    Optional<PointTransaction> findByReportIdAndTxType(UUID reportId, TxType txType);

    long countByReportIdAndTxType(UUID reportId, TxType txType);

    Page<PointTransaction> findAllByUserIdOrderByCreatedAtDesc(UUID userId, Pageable pageable);

    @Query("select coalesce(sum(pt.points), 0) from PointTransaction pt where pt.userId = :userId")
    long sumPointsByUserId(@Param("userId") UUID userId);

    @Query(value = """
        SELECT
            pt.user_id AS userId,
            u.full_name AS fullName,
            COALESCE(SUM(pt.points), 0) AS totalPoints
        FROM point_transactions pt
        JOIN waste_reports wr ON wr.id = pt.report_id
        JOIN users u ON u.id = pt.user_id
        WHERE wr.area_id = :areaId
          AND pt.tx_type = 'EARN'
          AND pt.created_at >= (NOW() - INTERVAL :days DAY)
        GROUP BY pt.user_id, u.full_name
        ORDER BY totalPoints DESC, pt.user_id ASC
        LIMIT :limit
        """, nativeQuery = true)
    List<LeaderboardRowProjection> findAreaLeaderboard(@Param("areaId") String areaId,
                                                        @Param("days") int days,
                                                        @Param("limit") int limit);
}
