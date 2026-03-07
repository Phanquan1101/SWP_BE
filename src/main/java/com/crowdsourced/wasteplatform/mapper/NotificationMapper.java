package com.crowdsourced.wasteplatform.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.crowdsourced.wasteplatform.dto.notification.NotificationResponse;
import com.crowdsourced.wasteplatform.entity.Notification;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NotificationMapper {
    NotificationResponse toResponse(Notification notification);
}
