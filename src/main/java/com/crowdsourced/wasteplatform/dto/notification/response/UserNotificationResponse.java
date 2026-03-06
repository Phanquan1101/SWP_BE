package com.crowdsourced.wasteplatform.dto.notification.response;

import java.time.Instant;
import java.util.UUID;

import com.crowdsourced.wasteplatform.dto.notification.NotificationResponse;
import com.crowdsourced.wasteplatform.entity.NotificationChannel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserNotificationResponse {
    private UUID id;

    private boolean read;

    private Instant readAt;

    private NotificationChannel deliveryChannel;

    private NotificationResponse notification;

}
