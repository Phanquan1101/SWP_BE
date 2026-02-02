package com.crowdsourced.wasteplatform.repository;

import com.crowdsourced.wasteplatform.entity.Notification;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
}
