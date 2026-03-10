package com.crowdsourced.wasteplatform.repository;

import com.crowdsourced.wasteplatform.entity.Voucher;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;

public interface VoucherRepository extends JpaRepository<Voucher, UUID> {

    Optional<Voucher> findByCode(String code);

    boolean existsByCode(String code);

    @Query("""
        select v from Voucher v
        where (:keyword is null
            or lower(v.code) like lower(concat('%', :keyword, '%'))
            or lower(v.title) like lower(concat('%', :keyword, '%')))
        """)
    Page<Voucher> searchEnterpriseVouchers(@Param("keyword") String keyword, Pageable pageable);

    Page<Voucher> findByActiveTrue(Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select v from Voucher v where v.id = :id")
    Optional<Voucher> findByIdForUpdate(@Param("id") UUID id);
}
