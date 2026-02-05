package com.crowdsourced.wasteplatform.repository;

import com.crowdsourced.wasteplatform.entity.Area;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaRepository extends JpaRepository<Area, UUID> {

    List<Area> findByActiveTrue();
}
