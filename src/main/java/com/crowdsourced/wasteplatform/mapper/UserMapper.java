package com.crowdsourced.wasteplatform.mapper;

import com.crowdsourced.wasteplatform.dto.admin.request.UpsertUserRequest;
import com.crowdsourced.wasteplatform.dto.admin.response.UserAdminResponse;
import com.crowdsourced.wasteplatform.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void upsertUserRequest(UpsertUserRequest request, @MappingTarget User user);

    UserAdminResponse toAdminResponse(User user);

}

