package com.crowdsourced.wasteplatform.service.enterprise;

import com.crowdsourced.wasteplatform.dto.common.PageResponse;
import com.crowdsourced.wasteplatform.dto.enterprise.response.CollectorPickResponse;
import com.crowdsourced.wasteplatform.entity.Area;
import com.crowdsourced.wasteplatform.entity.UserStatus;
import com.crowdsourced.wasteplatform.exception.AppException;
import com.crowdsourced.wasteplatform.exception.ErrorCode;
import com.crowdsourced.wasteplatform.mapper.AdminMapper;
import com.crowdsourced.wasteplatform.repository.AreaRepository;
import com.crowdsourced.wasteplatform.repository.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EnterpriseCollectorService {

    private final UserRepository userRepository;
    private final AreaRepository areaRepository;
    private final AdminMapper adminMapper;

    @Transactional(readOnly = true)
    public PageResponse<CollectorPickResponse> getCollectors(String areaId, Pageable pageable) {
        UUID areaUuid = null;
        if (areaId != null && !areaId.isBlank()) {
            areaUuid = parseUuid(areaId, "areaId");
            Area area = areaRepository.findById(areaUuid)
                .orElseThrow(() -> new AppException(ErrorCode.AREA_NOT_FOUND, "Area not found"));
            if (!area.isActive()) {
                throw new AppException(ErrorCode.AREA_INACTIVE, "Area is inactive");
            }
        }

        Page<CollectorPickResponse> page = userRepository.findCollectors(areaUuid, UserStatus.ACTIVE, "ROLE_COLLECTOR", pageable)
            .map(adminMapper::toCollectorPick);
        return PageResponse.from(page);
    }

    private UUID parseUuid(String value, String field) {
        try {
            return UUID.fromString(value);
        } catch (Exception ex) {
            throw new AppException(ErrorCode.BAD_REQUEST, "Invalid UUID for " + field);
        }
    }
}
