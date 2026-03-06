package com.crowdsourced.wasteplatform.repository;

import com.crowdsourced.wasteplatform.entity.UserNotification;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification, UUID> {

    @EntityGraph(attributePaths = {"notification"})
    List<UserNotification> findByUser_Id(UUID id);

    List<UserNotification> findByUserIdAndIdIn(
            UUID userId,
            List<UUID> ids
    );
}
