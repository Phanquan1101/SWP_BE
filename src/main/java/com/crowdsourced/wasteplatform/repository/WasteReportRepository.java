package com.crowdsourced.wasteplatform.repository;

import com.crowdsourced.wasteplatform.entity.WasteReport;
import com.crowdsourced.wasteplatform.entity.ReportStatus;
import java.util.Optional;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WasteReportRepository extends JpaRepository<WasteReport, UUID> {

    List<WasteReport> findByCitizenIdOrderByCreatedAtDesc(UUID citizenId);

    Page<WasteReport> findAllByCitizenIdOrderByCreatedAtDesc(UUID citizenId, Pageable pageable);

    Optional<WasteReport> findByIdAndCitizenId(UUID id, UUID citizenId);

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
}
