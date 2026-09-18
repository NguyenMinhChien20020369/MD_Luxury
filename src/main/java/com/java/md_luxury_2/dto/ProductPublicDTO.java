package com.java.md_luxury_2.dto;

import com.java.md_luxury_2.entity.Product;

public record ProductPublicDTO(
        String id,
        String name,
        String slug,
        String description,
        String coverImageUrl
) {
    public static ProductPublicDTO from(Product product, String coverImageUrl) {
        return new ProductPublicDTO(
                product.getId() != null ? product.getId().toString() : null,
                product.getName(),
                product.getSlug(),
                product.getDescription(),
                coverImageUrl
        );
    }
}