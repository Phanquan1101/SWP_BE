package com.crowdsourced.wasteplatform.service.notification;

import java.security.Principal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.crowdsourced.utils.SecurityUtil;
import com.crowdsourced.wasteplatform.entity.User;
import com.crowdsourced.wasteplatform.entity.UserNotification;
import com.crowdsourced.wasteplatform.repository.UserNotificationRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserNotificationService {
    private final UserNotificationRepository userNotificationRepository;
    private final SecurityUtil securityUtil;

    @Transactional
    public List<UserNotification> updateReaded(
        Principal principal,
        List<UUID> notificationIds) {

    User user = securityUtil.getLoginUser(principal);

    // 1️⃣ Lấy những notification mà:
    // - id nằm trong list
    // - complainant.id = user.id
    List<UserNotification> notifications =
            userNotificationRepository
                .findByNotification_IdInAndNotification_Complaint_Complainant_Id(
                        notificationIds,
                        user.getId()
                );

    // 2️⃣ Check tồn tại & ownership
    if (notifications.size() != notificationIds.size()) {
        throw new RuntimeException("Some notifications not found or not owned by user");
    }
    Instant now = Instant.now();

    notifications.forEach(n -> {
        if (!n.isRead()) {
            n.setRead(true);
            n.setReadAt(now);
        }
    });

    return userNotificationRepository.saveAll(notifications);
    }
}
