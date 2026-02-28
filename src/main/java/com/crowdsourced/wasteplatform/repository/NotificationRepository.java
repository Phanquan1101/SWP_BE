package com.crowdsourced.wasteplatform.repository;

import com.crowdsourced.wasteplatform.entity.Notification;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {
}
