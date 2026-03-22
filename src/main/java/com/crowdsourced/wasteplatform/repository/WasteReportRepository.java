package com.crowdsourced.wasteplatform.repository;

import com.crowdsourced.wasteplatform.entity.WasteReport;
import com.crowdsourced.wasteplatform.entity.ReportStatus;
import com.crowdsourced.wasteplatform.repository.projection.AdminReportsByMonthProjection;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WasteReportRepository extends JpaRepository<WasteReport, UUID> {

    List<WasteReport> findByCitizenIdOrderByCreatedAtDesc(UUID citizenId);

    Page<WasteReport> findAllByCitizenIdOrderByCreatedAtDesc(UUID citizenId, Pageable pageable);

    Optional<WasteReport> findByIdAndCitizenId(UUID id, UUID citizenId);

    boolean existsByCitizenIdAndAreaIdAndWasteCategoryIdAndCreatedAtGreaterThanEqualAndCurrentStatusIn(
        UUID citizenId,
        UUID areaId,
        UUID wasteCategoryId,
        Instant createdAt,
        List<ReportStatus> statuses
    );

    @Query("""
        select wr from WasteReport wr
        where (:areaId is null or wr.areaId = :areaId)
          and (:status is null or wr.currentStatus = :status)
          and exists (
              select wc.id from WasteCapability wc
              where wc.wasteCategoryId = wr.wasteCategoryId
                and wc.accepting = true
          )
        """)
    Page<WasteReport> findInbox(@Param("areaId") UUID areaId,
                                @Param("status") ReportStatus status,
                                Pageable pageable);

    @EntityGraph(attributePaths = {"citizen", "area", "wasteCategory", "mediaList"})
    Optional<WasteReport> findById(UUID id);

    @Query("""
        select count(wr) from WasteReport wr
        where wr.createdAt between :start and :end
          and wr.currentStatus in :statuses
        """)
    long countByCreatedAtRangeAndStatuses(@Param("start") Instant start,
                                          @Param("end") Instant end,
                                          @Param("statuses") List<ReportStatus> statuses);

    @Query("""
        select coalesce(sum(coalesce(wr.actualWeightKg, wr.estimatedWeightKg)), 0)
        from WasteReport wr
        where wr.createdAt between :start and :end
          and wr.currentStatus in :statuses
        """)
    BigDecimal sumWeightByCreatedAtRangeAndStatuses(@Param("start") Instant start,
                                                    @Param("end") Instant end,
                                                    @Param("statuses") List<ReportStatus> statuses);

    @Query("""
        select count(wr) from WasteReport wr
        where wr.citizenId = :citizenId
          and wr.createdAt between :start and :end
        """)
    long countByCitizenIdAndCreatedAtBetween(@Param("citizenId") UUID citizenId,
                                             @Param("start") Instant start,
                                             @Param("end") Instant end);

    @Query(value = """
        SELECT
            DATE_FORMAT(wr.created_at, '%Y-%m') AS month,
            COUNT(*) AS reportCount
        FROM waste_reports wr
        WHERE YEAR(wr.created_at) = :year
        GROUP BY DATE_FORMAT(wr.created_at, '%Y-%m')
        ORDER BY month ASC
        """, nativeQuery = true)
    List<AdminReportsByMonthProjection> countReportsByMonth(@Param("year") int year);

    @Query("""
        select coalesce(sum(coalesce(wr.actualWeightKg, wr.estimatedWeightKg)), 0)
        from WasteReport wr
        where wr.citizenId = :citizenId
          and wr.currentStatus in :statuses
        """)
    BigDecimal sumCollectedKgByCitizenId(@Param("citizenId") UUID citizenId,
                                         @Param("statuses") List<ReportStatus> statuses);
}
