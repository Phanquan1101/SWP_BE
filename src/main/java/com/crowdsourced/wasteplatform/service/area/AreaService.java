package com.crowdsourced.wasteplatform.service.area;

import com.crowdsourced.wasteplatform.dto.area.request.CreateAreaRequest;
import com.crowdsourced.wasteplatform.dto.area.request.UpdateAreaRequest;
import com.crowdsourced.wasteplatform.dto.area.response.AreaResponse;
import com.crowdsourced.wasteplatform.entity.Area;
import com.crowdsourced.wasteplatform.exception.AppException;
import com.crowdsourced.wasteplatform.exception.ErrorCode;
import com.crowdsourced.wasteplatform.mapper.AreaMapper;
import com.crowdsourced.wasteplatform.repository.AreaRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
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
}
