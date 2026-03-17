package com.crowdsourced.wasteplatform.repository;

import com.crowdsourced.wasteplatform.entity.User;
import com.crowdsourced.wasteplatform.entity.UserStatus;
import com.crowdsourced.wasteplatform.repository.projection.AdminCollectorsByAreaProjection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);

    Optional<User> findByEmailOrPhone(String email, String phone);

    @Query("""
        select u from User u
        where (:q is null
            or lower(u.email) like lower(concat('%', :q, '%'))
            or lower(coalesce(u.phone, '')) like lower(concat('%', :q, '%'))
            or lower(coalesce(u.fullName, '')) like lower(concat('%', :q, '%')))
          and (:status is null or u.status = :status)
          and (:roleCode is null or (
              select count(ur) from UserRole ur
              join ur.role r
              where ur.userId = u.id and r.code = :roleCode
          ) > 0)
        """)
    Page<User> searchUsers(@Param("q") String q,
                           @Param("roleCode") String roleCode,
                           @Param("status") UserStatus status,
                           Pageable pageable);

    @Query("""
        select u from User u
        where u.status = :status
          and (:areaId is null or u.areaId = :areaId)
          and (
              select count(ur) from UserRole ur
              join ur.role r
              where ur.userId = u.id and r.code = :collectorRoleCode
          ) > 0
        """)
    Page<User> findCollectors(@Param("areaId") UUID areaId,
                              @Param("status") UserStatus status,
                              @Param("collectorRoleCode") String collectorRoleCode,
                              Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select u from User u where u.id = :id")
    Optional<User> findByIdForUpdate(@Param("id") UUID id);

    @Query(value = """
        SELECT COUNT(DISTINCT u.id)
        FROM users u
        JOIN user_roles ur ON ur.user_id = u.id
        JOIN roles r ON r.id = ur.role_id
        WHERE r.code = :collectorRoleCode
          AND u.status = 'ACTIVE'
        """, nativeQuery = true)
    long countActiveCollectors(@Param("collectorRoleCode") String collectorRoleCode);

    @Query(value = """
        SELECT
            a.id AS areaId,
            a.name AS areaName,
            COUNT(DISTINCT u.id) AS collectorCount
        FROM users u
        JOIN user_roles ur ON ur.user_id = u.id
        JOIN roles r ON r.id = ur.role_id
        JOIN areas a ON a.id = u.area_id
        WHERE r.code = :collectorRoleCode
        GROUP BY a.id, a.name
        ORDER BY a.name ASC
        """, nativeQuery = true)
    List<AdminCollectorsByAreaProjection> countCollectorsByArea(@Param("collectorRoleCode") String collectorRoleCode);
}
