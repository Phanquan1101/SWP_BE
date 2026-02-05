package com.crowdsourced.wasteplatform.service.waste_category;

import com.crowdsourced.wasteplatform.dto.waste_category.request.CreateWasteCategoryRequest;
import com.crowdsourced.wasteplatform.dto.waste_category.request.UpdateWasteCategoryRequest;
import com.crowdsourced.wasteplatform.dto.waste_category.response.WasteCategoryResponse;
import com.crowdsourced.wasteplatform.entity.WasteCategory;
import com.crowdsourced.wasteplatform.exception.AppException;
import com.crowdsourced.wasteplatform.exception.ErrorCode;
import com.crowdsourced.wasteplatform.mapper.WasteCategoryMapper;
import com.crowdsourced.wasteplatform.repository.WasteCategoryRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WasteCategoryService {

    private final WasteCategoryRepository repository;
    private final WasteCategoryMapper mapper;

    @Transactional(readOnly = true)
    public List<WasteCategoryResponse> list(boolean includeInactive) {
        List<WasteCategory> entities = includeInactive ? repository.findAll() : repository.findByActiveTrue();
        return entities.stream().map(mapper::toResponse).toList();
    }

    @Transactional
    public WasteCategoryResponse create(CreateWasteCategoryRequest request) {
        repository.findByCode(request.getCode()).ifPresent(c -> {
            throw new AppException(ErrorCode.CONFLICT, "Code already exists");
        });
        WasteCategory entity = WasteCategory.builder()
            .id(UUID.randomUUID())
            .code(request.getCode())
            .name(request.getName())
            .active(true)
            .build();
        WasteCategory saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Transactional
    public WasteCategoryResponse update(UUID id, UpdateWasteCategoryRequest request) {
        WasteCategory entity = repository.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Waste category not found"));
        entity.setName(request.getName());
        WasteCategory saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Transactional
    public void deactivate(UUID id) {
        WasteCategory entity = repository.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Waste category not found"));
        entity.setActive(false);
        repository.save(entity);
    }
}
