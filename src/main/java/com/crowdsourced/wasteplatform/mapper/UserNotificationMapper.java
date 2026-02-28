package com.crowdsourced.wasteplatform.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.crowdsourced.wasteplatform.dto.notification.response.UserNotificationResponse;
import com.crowdsourced.wasteplatform.entity.UserNotification;

@Mapper(
        componentModel = "spring",
        uses = NotificationMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserNotificationMapper {

    @Mapping(source = "notification", target = "notification")
    UserNotificationResponse toResponse(UserNotification userNotification);
}
