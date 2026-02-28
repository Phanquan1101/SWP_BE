package com.crowdsourced.wasteplatform.repository;

import com.crowdsourced.wasteplatform.entity.UserNotification;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification, UUID> {
    List<UserNotification> findByNotification_IdInAndNotification_Complaint_Complainant_Id(
            List<UUID> notificationIds,
            UUID userId
    );
}
