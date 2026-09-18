package com.java.md_luxury_2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.*;

@Entity
@Table(name = "product", schema = "content")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue
    private UUID id;

    private String sku;
    private String name;
    private String slug;
    private String description;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    private UUID createdBy;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductImage> images = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @PrePersist
    protected void onCreate() {
        OffsetDateTime now = OffsetDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }

    // Định nghĩa các luồng chuyển trạng thái hợp lệ
    private static final Map<ProductStatus, Set<ProductStatus>> ALLOWED_TRANSITIONS = Map.of(
            ProductStatus.DRAFT, Set.of(ProductStatus.PENDING),
            ProductStatus.PENDING, Set.of(ProductStatus.PUBLISHED, ProductStatus.DRAFT),
            ProductStatus.PUBLISHED, Set.of(ProductStatus.UNPUBLISHED),
            ProductStatus.UNPUBLISHED, Set.of(ProductStatus.DRAFT)
    );

    // Method nghiệp vụ kiểm tra và chuyển trạng thái
    public void transitionTo(ProductStatus target) {
        Set<ProductStatus> allowed = ALLOWED_TRANSITIONS.get(this.status);
        if (allowed == null || !allowed.contains(target)) {
            throw new IllegalStateException(
                    "Không thể chuyển trạng thái từ " + this.status + " sang " + target);
        }
        this.status = target;
        this.updatedAt = OffsetDateTime.now();
    }
}