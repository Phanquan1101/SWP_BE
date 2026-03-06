package com.crowdsourced.wasteplatform.controller;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crowdsourced.wasteplatform.dto.notification.response.UserNotificationResponse;
import com.crowdsourced.wasteplatform.exception.ApiResponse;
import com.crowdsourced.wasteplatform.service.notification.NotificationService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @Operation(summary = "List notification")
    @GetMapping("/citizen/notifications")
    public ResponseEntity<ApiResponse<List<UserNotificationResponse>>> getNotifications(Principal principal) {
        List<UserNotificationResponse> data = notificationService.getUserNotifications(principal);
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @Operation(summary = "Update readed")
    @PutMapping("/citizen/notification")
    public ResponseEntity<ApiResponse<List<UserNotificationResponse>>> updateReaded(
            Principal principal,
            @RequestParam(required = false) List<UUID> ids
    ) {
        List<UserNotificationResponse> response = notificationService.updateReaded(principal, ids);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
