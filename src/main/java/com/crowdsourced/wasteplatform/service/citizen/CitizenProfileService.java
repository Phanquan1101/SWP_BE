package com.crowdsourced.wasteplatform.service.citizen;

import com.crowdsourced.wasteplatform.dto.citizen.request.UpdateCitizenProfileRequest;
import com.crowdsourced.wasteplatform.dto.citizen.response.CitizenProfileResponse;
import com.crowdsourced.wasteplatform.entity.Area;
import com.crowdsourced.wasteplatform.entity.ReportStatus;
import com.crowdsourced.wasteplatform.entity.User;
import com.crowdsourced.wasteplatform.exception.AppException;
import com.crowdsourced.wasteplatform.exception.ErrorCode;
import com.crowdsourced.wasteplatform.repository.AreaRepository;
import com.crowdsourced.wasteplatform.repository.UserRepository;
import com.crowdsourced.wasteplatform.repository.WasteReportRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CitizenProfileService {

    private static final List<ReportStatus> COLLECTED_STATUSES = List.of(ReportStatus.COLLECTED, ReportStatus.COMPLETED);

    private final UserRepository userRepository;
    private final AreaRepository areaRepository;
    private final WasteReportRepository wasteReportRepository;

    public CitizenProfileService(UserRepository userRepository,
                                 AreaRepository areaRepository,
                                 WasteReportRepository wasteReportRepository) {
        this.userRepository = userRepository;
        this.areaRepository = areaRepository;
        this.wasteReportRepository = wasteReportRepository;
    }

    @Transactional(readOnly = true)
    public CitizenProfileResponse getMyProfile(String citizenId) {
        UUID userId = parseUuid(citizenId, "citizenId");
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND, "User not found"));

        String areaName = null;
        if (user.getAreaId() != null) {
            areaName = areaRepository.findById(user.getAreaId())
                .map(Area::getName)
                .orElse(null);
        }

        BigDecimal totalCollectedKg = wasteReportRepository.sumCollectedKgByCitizenId(userId, COLLECTED_STATUSES);
        if (totalCollectedKg == null) {
            totalCollectedKg = BigDecimal.ZERO;
        }

        return CitizenProfileResponse.builder()
            .fullName(user.getFullName())
            .email(user.getEmail())
            .phone(user.getPhone())
            .area(areaName)
            .totalCollectedKg(totalCollectedKg)
            .build();
    }

    @Transactional
    public CitizenProfileResponse updateMyProfile(String citizenId, UpdateCitizenProfileRequest request) {
        UUID userId = parseUuid(citizenId, "citizenId");
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND, "User not found"));

        if (request.getFullName() != null) {
            String fullName = request.getFullName().trim();
            if (fullName.isEmpty()) {
                throw new AppException(ErrorCode.BAD_REQUEST, "fullName must not be blank");
            }
            user.setFullName(fullName);
        }

        if (request.getPhone() != null) {
            String phone = request.getPhone().trim();
            if (phone.isEmpty()) {
                user.setPhone(null);
            } else if (!phone.equals(user.getPhone())) {
                userRepository.findByPhone(phone).ifPresent(existing -> {
                    if (!existing.getId().equals(userId)) {
                        throw new AppException(ErrorCode.CONFLICT, "Phone already in use");
                    }
                });
                user.setPhone(phone);
            }
        }

        if (request.getAreaId() != null) {
            String areaIdRaw = request.getAreaId().trim();
            if (areaIdRaw.isEmpty()) {
                user.setAreaId(null);
            } else {
                UUID areaId = parseUuid(areaIdRaw, "areaId");
                Area area = areaRepository.findById(areaId)
                    .orElseThrow(() -> new AppException(ErrorCode.AREA_NOT_FOUND, "Area not found"));
                if (!area.isActive()) {
                    throw new AppException(ErrorCode.AREA_INACTIVE, "Area is inactive");
                }
                user.setAreaId(areaId);
            }
        }

        userRepository.save(user);
        return getMyProfile(citizenId);
    }

    private UUID parseUuid(String value, String field) {
        try {
            return UUID.fromString(value);
        } catch (Exception ex) {
            throw new AppException(ErrorCode.BAD_REQUEST, "Invalid UUID for " + field);
        }
    }
}
