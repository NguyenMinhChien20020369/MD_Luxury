package com.java.md_luxury_2.repository;

import com.java.md_luxury_2.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    // Mở rộng thêm: Lấy tất cả danh mục và tự động sắp xếp tăng dần theo display_order
    List<Category> findAllByOrderByDisplayOrderAsc();
}