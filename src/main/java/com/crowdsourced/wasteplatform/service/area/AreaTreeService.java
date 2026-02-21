package com.crowdsourced.wasteplatform.service.area;

import com.crowdsourced.wasteplatform.dto.area.response.AreaTreeNodeResponse;
import com.crowdsourced.wasteplatform.entity.Area;
import com.crowdsourced.wasteplatform.exception.AppException;
import com.crowdsourced.wasteplatform.exception.ErrorCode;
import com.crowdsourced.wasteplatform.repository.AreaRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AreaTreeService {

    private final AreaRepository areaRepository;

    @Value("${app.area.root-id:}")
    private String configuredRootId;

    @Transactional(readOnly = true)
    public AreaTreeNodeResponse getHcmAreaTree() {
        List<Area> allAreas = areaRepository.findByActiveTrue();
        if (allAreas.isEmpty()) {
            throw new AppException(ErrorCode.AREA_ROOT_NOT_FOUND, "No active area found");
        }

        Area root = resolveRoot(allAreas);
        Map<UUID, List<Area>> childrenMap = new HashMap<>();
        for (Area area : allAreas) {
            if (area.getParentId() != null) {
                childrenMap.computeIfAbsent(area.getParentId(), key -> new ArrayList<>()).add(area);
            }
        }
        childrenMap.values().forEach(list -> list.sort(Comparator.comparing(Area::getName)));

        return buildNode(root, childrenMap);
    }

    private Area resolveRoot(List<Area> allAreas) {
        if (configuredRootId != null && !configuredRootId.isBlank()) {
            try {
                UUID rootId = UUID.fromString(configuredRootId);
                for (Area area : allAreas) {
                    if (area.getId().equals(rootId)) {
                        return area;
                    }
                }
            } catch (IllegalArgumentException ignored) {
                // Ignore invalid config and fallback to name-based lookup.
            }
        }

        return allAreas.stream()
            .filter(area -> "TP.HCM".equalsIgnoreCase(area.getName()))
            .findFirst()
            .orElseThrow(() -> new AppException(ErrorCode.AREA_ROOT_NOT_FOUND, "TP.HCM root not found"));
    }

    private AreaTreeNodeResponse buildNode(Area current, Map<UUID, List<Area>> childrenMap) {
        List<AreaTreeNodeResponse> children = childrenMap.getOrDefault(current.getId(), List.of())
            .stream()
            .map(child -> buildNode(child, childrenMap))
            .toList();

        return AreaTreeNodeResponse.builder()
            .id(current.getId().toString())
            .name(current.getName())
            .children(children)
            .build();
    }
}
