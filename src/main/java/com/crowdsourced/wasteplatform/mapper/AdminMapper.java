package com.crowdsourced.wasteplatform.mapper;

import com.crowdsourced.wasteplatform.dto.admin.user.response.UserDetailResponse;
import com.crowdsourced.wasteplatform.dto.admin.user.response.UserSummaryResponse;
import com.crowdsourced.wasteplatform.dto.enterprise.response.CollectorPickResponse;
import com.crowdsourced.wasteplatform.entity.User;
import java.util.List;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "workingAreaId", source = "user.areaId")
    UserSummaryResponse toUserSummary(User user, List<String> roles);

    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "workingAreaId", source = "user.areaId")
    UserDetailResponse toUserDetail(User user, List<String> roles);

    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "areaId", source = "user.areaId")
    @Mapping(target = "email", expression = "java(maskEmail(user))")
    CollectorPickResponse toCollectorPick(User user);

    default String map(UUID value) {
        return value == null ? null : value.toString();
    }

    default String maskEmail(User user) {
        String email = user == null ? null : user.getEmail();
        if (email == null || email.isBlank()) {
            return null;
        }
        int atIndex = email.indexOf('@');
        if (atIndex <= 1) {
            return "***";
        }
        return email.charAt(0) + "***" + email.substring(atIndex);
    }
}
