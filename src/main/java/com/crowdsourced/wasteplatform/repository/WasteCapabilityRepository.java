package com.crowdsourced.wasteplatform.repository;

import com.crowdsourced.wasteplatform.entity.WasteCapability;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WasteCapabilityRepository extends JpaRepository<WasteCapability, UUID> {

    Optional<WasteCapability> findByWasteCategoryId(UUID wasteCategoryId);
}
