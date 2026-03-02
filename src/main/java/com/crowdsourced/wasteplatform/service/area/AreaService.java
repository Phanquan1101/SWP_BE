package com.crowdsourced.wasteplatform.service.area;

import com.crowdsourced.wasteplatform.dto.area.request.CreateAreaRequest;
import com.crowdsourced.wasteplatform.dto.area.request.UpdateAreaRequest;
import com.crowdsourced.wasteplatform.dto.area.response.AreaResponse;
import com.crowdsourced.wasteplatform.dto.area.response.AreaTreeNodeResponse;
import com.crowdsourced.wasteplatform.entity.Area;
import com.crowdsourced.wasteplatform.exception.AppException;
import com.crowdsourced.wasteplatform.exception.ErrorCode;
import com.crowdsourced.wasteplatform.mapper.AreaMapper;
import com.crowdsourced.wasteplatform.repository.AreaRepository;

import java.util.*;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AreaService {

    private final AreaRepository areaRepository;
    private final AreaMapper areaMapper;

    @Transactional(readOnly = true)
    public List<AreaResponse> getAllActive() {
        return areaRepository.findByActiveTrue().stream()
            .map(areaMapper::toResponse)
            .toList();
    }

    @Transactional
    public AreaResponse create(CreateAreaRequest request) {
        Area area = Area.builder()
            .id(UUID.randomUUID())
            .parentId(parseUuid(request.getParentId()))
            .name(request.getName())
            .active(true)
            .build();
        Area saved = areaRepository.save(area);
        return areaMapper.toResponse(saved);
    }

    @Transactional
    public AreaResponse update(UUID id, UpdateAreaRequest request) {
        Area area = areaRepository.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Area not found"));
        area.setName(request.getName());
        area.setParentId(parseUuid(request.getParentId()));
        Area saved = areaRepository.save(area);
        return areaMapper.toResponse(saved);
    }

    @Transactional
    public void deactivate(UUID id) {
        Area area = areaRepository.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Area not found"));
        area.setActive(false);
        areaRepository.save(area);
    }

    private UUID parseUuid(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return UUID.fromString(value);
    }

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
