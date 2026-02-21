package com.crowdsourced.wasteplatform.repository;

import com.crowdsourced.wasteplatform.entity.UserRole;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {

    @Query("select ur from UserRole ur join fetch ur.role where ur.userId = :userId")
    List<UserRole> findByUserIdWithRole(@Param("userId") UUID userId);

    @Query("""
        select case when count(ur) > 0 then true else false end
        from UserRole ur join ur.role r
        where ur.userId = :userId and r.code = :roleCode
        """)
    boolean existsByUserIdAndRoleCode(@Param("userId") UUID userId, @Param("roleCode") String roleCode);
}
