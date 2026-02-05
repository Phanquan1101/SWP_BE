package com.crowdsourced.wasteplatform.repository;

import com.crowdsourced.wasteplatform.entity.Complaint;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintRepository extends JpaRepository<Complaint, UUID> {
}
