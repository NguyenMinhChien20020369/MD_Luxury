package com.java.md_luxury_2.repository;

import com.java.md_luxury_2.entity.Product;
import com.java.md_luxury_2.entity.ProductStatus;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {

    public static Specification<Product> isPublished() {
        return (root, query, cb) -> cb.equal(root.get("status"), ProductStatus.PUBLISHED);
    }

    public static Specification<Product> hasCategorySlug(String categorySlug) {
        return (root, query, cb) -> {
            if (categorySlug == null || categorySlug.isBlank()) {
                return cb.conjunction();
            }
            return cb.equal(root.get("category").get("slug"), categorySlug);
        };
    }
}