package com.crowdsourced.wasteplatform.repository;

import com.crowdsourced.wasteplatform.entity.User;
import java.util.Optional;
import java.util.UUID;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);

    Optional<User> findByEmailOrPhone(String email, String phone);

    Boolean existsByEmail(String email);
    Boolean existsByPhone(String phone);
    boolean existsByEmailAndIdNot(String email, UUID id);
    boolean existsByPhoneAndIdNot(String phone, UUID id);

    @EntityGraph(attributePaths = {"enterprise", "area"})
    @Query("""
        SELECT u FROM User u
        WHERE
            (:keyword IS NULL OR
             LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
             LOWER(u.phone) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
             LOWER(u.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')))
        AND (:userType IS NULL OR u.userType = :userType)
        AND (:status IS NULL OR u.status = :status)
    """)
    Page<User> searchUsers(
            @Param("keyword") String keyword,
            @Param("userType") User.UserType userType,
            @Param("status") User.UserStatus status,
            Pageable pageable
    );

    @Override
    @NonNull
    @EntityGraph(attributePaths = {"enterprise", "area"})
    Optional<User> findById(@NonNull UUID id);

}
