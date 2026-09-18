package com.java.md_luxury_2.dto;

import com.java.md_luxury_2.entity.Product;
import com.java.md_luxury_2.entity.ProductStatus;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

public record ProductAdminDTO(
        String id,
        String sku,
        String name,
        String slug,
        String description,
        ProductStatus status,
        UUID createdBy,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
    public static ProductAdminDTO from(Product product) {
        return new ProductAdminDTO(
                product.getId() != null ? product.getId().toString() : null,
                product.getSku(),
                product.getName(),
                product.getSlug(),
                product.getDescription(),
                product.getStatus(),
                product.getCreatedBy(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}