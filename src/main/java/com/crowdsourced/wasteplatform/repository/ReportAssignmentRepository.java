package com.crowdsourced.wasteplatform.repository;

import com.crowdsourced.wasteplatform.entity.CollectorStatus;
import com.crowdsourced.wasteplatform.entity.ReportAssignment;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportAssignmentRepository extends JpaRepository<ReportAssignment, UUID> {

    Page<ReportAssignment> findAllByCollectorIdOrderByAssignedAtDesc(UUID collectorId, Pageable pageable);

    Page<ReportAssignment> findAllByCollectorIdAndCollectorStatusOrderByAssignedAtDesc(
        UUID collectorId, CollectorStatus status, Pageable pageable);

    Optional<ReportAssignment> findByIdAndCollectorId(UUID id, UUID collectorId);

    Optional<ReportAssignment> findByReportIdAndCollectorId(UUID reportId, UUID collectorId);

    boolean existsByReportId(UUID reportId);
}
