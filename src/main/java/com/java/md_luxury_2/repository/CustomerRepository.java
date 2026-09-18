package com.java.md_luxury_2.repository;

import com.java.md_luxury_2.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID>, JpaSpecificationExecutor<Customer> {

    // Khai báo tìm theo phone_hash (dùng cho Bài 19)
    Optional<Customer> findByPhoneHash(String phoneHash);

    // Truy vấn lấy danh sách khách hàng có sự kiện/kỷ niệm trong N ngày tới
    @Query(value = """
        SELECT * FROM crm.customer c
        WHERE c.created_at IS NOT NULL
          AND (
            EXTRACT(DOY FROM c.created_at) - EXTRACT(DOY FROM CURRENT_DATE)
            BETWEEN 0 AND :days
          )
        """, nativeQuery = true)
    List<Customer> findWithSpecialDateInNextDays(@Param("days") int days);

    // Kiểm tra sự tồn tại của số điện thoại qua phoneHash
    boolean existsByPhoneHash(String phoneHash);
}