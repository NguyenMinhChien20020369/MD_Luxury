package com.java.md_luxury_2.repository;

import com.java.md_luxury_2.entity.Customer;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class CustomerSpecifications {

    public static Specification<Customer> ownedBy(UUID ownerId) {
        return (root, query, cb) -> cb.equal(root.get("ownerId"), ownerId);
    }

    // Tìm kiếm theo tên (không phân biệt hoa thường)
    public static Specification<Customer> hasNameLike(String search) {
        return (root, query, cb) -> {
            if (search == null || search.trim().isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("fullName")), "%" + search.trim().toLowerCase() + "%");
        };
    }
}