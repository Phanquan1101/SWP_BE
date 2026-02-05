package com.crowdsourced.wasteplatform.repository;

import com.crowdsourced.wasteplatform.entity.PointTransaction;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointTransactionRepository extends JpaRepository<PointTransaction, UUID> {
}
