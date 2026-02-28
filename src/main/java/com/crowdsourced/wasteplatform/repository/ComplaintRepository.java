package com.crowdsourced.wasteplatform.repository;

import com.crowdsourced.wasteplatform.entity.Complaint;
import com.crowdsourced.wasteplatform.entity.ComplaintCategory;
import com.crowdsourced.wasteplatform.entity.ComplaintStatus;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, UUID> {
    @Query("""
        SELECT c FROM Complaint c
        WHERE
            (:category IS NULL OR c.category = :category)
        AND (:status IS NULL OR c.status = :status) 
        AND (:complainantId IS NULL OR c.complainantId = :complainantId)
    """)
    List<Complaint> searchComplaints(
            @Param("complainantId") UUID complainantId,
            @Param("category") ComplaintCategory category,
            @Param("status") ComplaintStatus status
    );
}
