package com.crowdsourced.wasteplatform.repository;

import com.crowdsourced.wasteplatform.entity.WasteCategory;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WasteCategoryRepository extends JpaRepository<WasteCategory, UUID> {

    Optional<WasteCategory> findByCode(String code);

    List<WasteCategory> findByActiveTrue();

    long countByActiveTrue();
}
