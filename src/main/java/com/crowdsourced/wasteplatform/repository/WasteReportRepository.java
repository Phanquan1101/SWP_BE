package com.crowdsourced.wasteplatform.repository;

import com.crowdsourced.wasteplatform.entity.WasteReport;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WasteReportRepository extends JpaRepository<WasteReport, UUID> {

    List<WasteReport> findByCitizenIdOrderByCreatedAtDesc(UUID citizenId);
    List<WasteReport> findByAreaId(UUID areaId);
}
