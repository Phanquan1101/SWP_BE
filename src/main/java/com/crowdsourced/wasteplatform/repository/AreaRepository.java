package com.crowdsourced.wasteplatform.repository;

import com.crowdsourced.wasteplatform.entity.Area;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaRepository extends JpaRepository<Area, UUID> {

    List<Area> findByActiveTrue();

    Optional<Area> findFirstByNameAndActiveTrue(String name);

    Optional<Area> findByIdAndActiveTrue(UUID id);

    long countByActiveTrue();
}
