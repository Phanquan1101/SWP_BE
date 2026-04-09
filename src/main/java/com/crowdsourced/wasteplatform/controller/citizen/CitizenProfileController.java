package com.crowdsourced.wasteplatform.controller.citizen;

import com.crowdsourced.wasteplatform.dto.citizen.request.UpdateCitizenProfileRequest;
import com.crowdsourced.wasteplatform.dto.citizen.response.CitizenProfileResponse;
import com.crowdsourced.wasteplatform.exception.ApiResponse;
import com.crowdsourced.wasteplatform.service.citizen.CitizenProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/citizen/profile")
@Tag(name = "Citizen - Profile")
public class CitizenProfileController {

    private final CitizenProfileService citizenProfileService;

    public CitizenProfileController(CitizenProfileService citizenProfileService) {
        this.citizenProfileService = citizenProfileService;
    }

    @GetMapping
    @Operation(summary = "Citizen xem profile cua minh")
    public ApiResponse<CitizenProfileResponse> getMyProfile() {
        return ApiResponse.success(citizenProfileService.getMyProfile(currentUserId()));
    }

    @PatchMapping
    @Operation(summary = "Citizen cap nhat profile cua minh")
    public ApiResponse<CitizenProfileResponse> updateMyProfile(@Valid @RequestBody UpdateCitizenProfileRequest request) {
        return ApiResponse.success(citizenProfileService.updateMyProfile(currentUserId(), request));
    }

    private String currentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof UUID uuid) {
            return uuid.toString();
        }
        return String.valueOf(principal);
    }
}
