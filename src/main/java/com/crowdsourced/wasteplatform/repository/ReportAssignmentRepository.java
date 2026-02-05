package com.crowdsourced.wasteplatform.repository;

import com.crowdsourced.wasteplatform.entity.ReportAssignment;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportAssignmentRepository extends JpaRepository<ReportAssignment, UUID> {
}
