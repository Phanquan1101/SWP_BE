package com.crowdsourced.wasteplatform.repository;

import com.crowdsourced.wasteplatform.entity.ReportMedia;
import com.crowdsourced.wasteplatform.entity.MediaType;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReportMediaRepository extends JpaRepository<ReportMedia, UUID> {
    List<ReportMedia> findByReportIdOrderByCreatedAtAsc(UUID reportId);

    @Query("""
        select count(rm) from ReportMedia rm
        where rm.createdBy = :collectorId
          and rm.mediaType = :mediaType
          and rm.createdAt between :start and :end
        """)
    long countByCollectorAndTypeAndRange(@Param("collectorId") UUID collectorId,
                                         @Param("mediaType") MediaType mediaType,
                                         @Param("start") Instant start,
                                         @Param("end") Instant end);
}
