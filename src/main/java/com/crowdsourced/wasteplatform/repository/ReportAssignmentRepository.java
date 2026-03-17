package com.crowdsourced.wasteplatform.repository;

import com.crowdsourced.wasteplatform.entity.CollectorStatus;
import com.crowdsourced.wasteplatform.entity.ReportAssignment;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReportAssignmentRepository extends JpaRepository<ReportAssignment, UUID> {

    Page<ReportAssignment> findAllByCollectorIdOrderByAssignedAtDesc(UUID collectorId, Pageable pageable);

    Page<ReportAssignment> findAllByCollectorIdAndCollectorStatusOrderByAssignedAtDesc(
        UUID collectorId, CollectorStatus status, Pageable pageable);

    Optional<ReportAssignment> findByIdAndCollectorId(UUID id, UUID collectorId);

    Optional<ReportAssignment> findByReportIdAndCollectorId(UUID reportId, UUID collectorId);

    boolean existsByReportId(UUID reportId);

    @Query("""
        select count(ra) from ReportAssignment ra
        where ra.collectorId = :collectorId
          and ra.assignedAt between :start and :end
        """)
    long countAssignedByCollectorAndRange(@Param("collectorId") UUID collectorId,
                                          @Param("start") Instant start,
                                          @Param("end") Instant end);

    @Query("""
        select count(ra) from ReportAssignment ra
        where ra.collectorId = :collectorId
          and ra.collectorStatus = :status
          and ra.assignedAt between :start and :end
        """)
    long countByCollectorStatusAndRange(@Param("collectorId") UUID collectorId,
                                        @Param("status") CollectorStatus status,
                                        @Param("start") Instant start,
                                        @Param("end") Instant end);
}
