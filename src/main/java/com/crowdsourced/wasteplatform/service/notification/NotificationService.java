package com.crowdsourced.wasteplatform.service.notification;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.crowdsourced.utils.SecurityUtil;
import com.crowdsourced.wasteplatform.dto.notification.NotificationResponse;
import com.crowdsourced.wasteplatform.entity.User;
import com.crowdsourced.wasteplatform.mapper.NotificationMapper;
import com.crowdsourced.wasteplatform.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final SecurityUtil securityUtil;
    private final NotificationMapper notificationMapper;

    public List<NotificationResponse> getNotifications(Principal principal){
        User user = securityUtil.getLoginUser(principal);
        List<NotificationResponse> notifications = notificationRepository.findByComplaint_Complainant_IdOrderByCreatedAtDesc(user.getId())
        .stream().map(notificationMapper::toResponse).toList();
        return notifications;
    }
}
