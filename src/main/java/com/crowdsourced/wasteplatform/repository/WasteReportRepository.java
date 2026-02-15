package com.crowdsourced.wasteplatform.repository;

import com.crowdsourced.wasteplatform.entity.WasteReport;
import java.util.Optional;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WasteReportRepository extends JpaRepository<WasteReport, UUID> {

    List<WasteReport> findByCitizenIdOrderByCreatedAtDesc(UUID citizenId);

    Page<WasteReport> findAllByCitizenIdOrderByCreatedAtDesc(UUID citizenId, Pageable pageable);

    Optional<WasteReport> findByIdAndCitizenId(UUID id, UUID citizenId);
}
