package com.java.md_luxury_2.repository;

import com.java.md_luxury_2.entity.Product;
import com.java.md_luxury_2.entity.ProductStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {

    // Tìm chi tiết sản phẩm công khai theo slug và trạng thái PUBLISHED
    Optional<Product> findBySlugAndStatus(String slug, ProductStatus status);

    List<Product> findByStatus(ProductStatus status);
}