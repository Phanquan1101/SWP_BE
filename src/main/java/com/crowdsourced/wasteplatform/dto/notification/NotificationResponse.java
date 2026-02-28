package com.crowdsourced.wasteplatform.dto.notification;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {
    private UUID id;

    private String eventType;

    private UUID reportId;

    private UUID complaintId;

    private String title;

    private String body;

    private Instant createdAt;
}
