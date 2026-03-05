package com.crowdsourced.wasteplatform.repository;

import com.crowdsourced.wasteplatform.entity.User;
import com.crowdsourced.wasteplatform.entity.UserStatus;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
}
