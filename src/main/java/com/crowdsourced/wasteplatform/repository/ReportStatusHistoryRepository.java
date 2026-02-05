package com.crowdsourced.wasteplatform.repository;

import com.crowdsourced.wasteplatform.entity.ReportStatusHistory;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportStatusHistoryRepository extends JpaRepository<ReportStatusHistory, UUID> {
}
