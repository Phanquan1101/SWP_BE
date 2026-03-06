package com.crowdsourced.wasteplatform.repository;

import com.crowdsourced.wasteplatform.entity.Complaint;
import com.crowdsourced.wasteplatform.entity.ComplaintCategory;
import com.crowdsourced.wasteplatform.entity.ComplaintStatus;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        ORDER BY c.createdAt DESC
    """)
    Page<Complaint> searchComplaints(
            @Param("complainantId") UUID complainantId,
            Pageable pageable,
            @Param("category") ComplaintCategory category,
            @Param("status") ComplaintStatus status
    );
}
