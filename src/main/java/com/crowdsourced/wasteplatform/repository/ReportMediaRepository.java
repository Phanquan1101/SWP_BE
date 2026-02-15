package com.crowdsourced.wasteplatform.repository;

import com.crowdsourced.wasteplatform.entity.ReportMedia;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportMediaRepository extends JpaRepository<ReportMedia, UUID> {
    List<ReportMedia> findByReportIdOrderByCreatedAtAsc(UUID reportId);
}
