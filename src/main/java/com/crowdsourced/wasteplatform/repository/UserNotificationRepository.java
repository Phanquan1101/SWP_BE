package com.crowdsourced.wasteplatform.repository;

import com.crowdsourced.wasteplatform.entity.UserNotification;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserNotificationRepository extends JpaRepository<UserNotification, UUID> {
}
