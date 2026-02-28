package com.crowdsourced.wasteplatform.service.notification;

import java.security.Principal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.crowdsourced.wasteplatform.dto.notification.response.UserNotificationResponse;
import com.crowdsourced.wasteplatform.entity.User;
import com.crowdsourced.wasteplatform.entity.UserNotification;
import com.crowdsourced.wasteplatform.mapper.UserNotificationMapper;
import com.crowdsourced.wasteplatform.repository.UserNotificationRepository;
import com.crowdsourced.wasteplatform.utils.SecurityUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final SecurityUtil securityUtil;

    private final UserNotificationRepository userNotificationRepository;
    private final UserNotificationMapper userNotificationMapper;

    @Transactional
    public List<UserNotificationResponse> updateReaded(
        Principal principal,
        List<UUID> notificationIds) {

    User user = securityUtil.getLoginUser(principal);

    List<UserNotification> notifications =
            userNotificationRepository
                .findByUserIdAndIdIn(user.getId(), notificationIds);

    if (notifications.size() != notificationIds.size()) {
        throw new RuntimeException("Some notifications not found or not owned by user");
    }

    notifications.forEach(n -> {
        if (!n.isRead()) {
            n.setRead(true);
            n.setReadAt(Instant.now());
        }
    });

    return notifications.stream().map(userNotificationMapper::toResponse).toList();
    }

    public List<UserNotificationResponse> getUserNotifications(Principal principal){
        User user = securityUtil.getLoginUser(principal);
        List<UserNotificationResponse> notifications = userNotificationRepository.findByUser_Id(user.getId())
        .stream().map(userNotificationMapper::toResponse).toList();
        return notifications;
    }
}
