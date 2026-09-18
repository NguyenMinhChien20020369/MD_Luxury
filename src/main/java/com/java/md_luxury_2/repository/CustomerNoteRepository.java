package com.java.md_luxury_2.repository;

import com.java.md_luxury_2.entity.CustomerNote;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface CustomerNoteRepository extends JpaRepository<CustomerNote, UUID> {

    // Tìm danh sách ghi chú theo ID khách hàng
    List<CustomerNote> findByCustomerIdOrderByCreatedAtDesc(UUID customerId);
}