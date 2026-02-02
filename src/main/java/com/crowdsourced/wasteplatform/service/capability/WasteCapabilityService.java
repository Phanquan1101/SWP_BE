package com.crowdsourced.wasteplatform.service.capability;

import com.crowdsourced.wasteplatform.dto.capability.request.UpsertWasteCapabilityRequest;
import com.crowdsourced.wasteplatform.dto.capability.response.WasteCapabilityResponse;
import com.crowdsourced.wasteplatform.entity.WasteCapability;
import com.crowdsourced.wasteplatform.entity.WasteCategory;
import com.crowdsourced.wasteplatform.exception.AppException;
import com.crowdsourced.wasteplatform.exception.ErrorCode;
import com.crowdsourced.wasteplatform.mapper.WasteCapabilityMapper;
import com.crowdsourced.wasteplatform.repository.WasteCapabilityRepository;
import com.crowdsourced.wasteplatform.repository.WasteCategoryRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WasteCapabilityService {

    private final WasteCapabilityRepository capabilityRepository;
    private final WasteCategoryRepository wasteCategoryRepository;
    private final WasteCapabilityMapper mapper;

    @Transactional(readOnly = true)
    public List<WasteCapabilityResponse> getAll() {
        return capabilityRepository.findAll().stream()
            .map(mapper::toResponse)
            .toList();
    }

    @Transactional
    public WasteCapabilityResponse upsert(UUID wasteCategoryId, UpsertWasteCapabilityRequest request) {
        WasteCategory category = wasteCategoryRepository.findById(wasteCategoryId)
            .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Waste category not found"));

        WasteCapability capability = capabilityRepository.findByWasteCategoryId(wasteCategoryId)
            .orElseGet(() -> WasteCapability.builder()
                .id(UUID.randomUUID())
                .wasteCategoryId(wasteCategoryId)
                .wasteCategory(category)
                .build());

        capability.setDailyCapacityKg(BigDecimal.valueOf(request.getDailyCapacityKg()));
        if (request.getAccepting() != null) {
            capability.setAccepting(request.getAccepting());
        } else if (capability.getId() == null) {
            capability.setAccepting(true);
        }

        WasteCapability saved = capabilityRepository.save(capability);
        return mapper.toResponse(saved);
    }

    @Transactional
    public WasteCapabilityResponse toggle(UUID wasteCategoryId) {
        WasteCapability capability = capabilityRepository.findByWasteCategoryId(wasteCategoryId)
            .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Capability not found"));
        capability.setAccepting(!capability.isAccepting());
        WasteCapability saved = capabilityRepository.save(capability);
        return mapper.toResponse(saved);
    }
}
