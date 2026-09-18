package com.java.md_luxury_2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "product_image", schema = "content")
@Getter
@Setter
public class ProductImage {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private String url;

    private Integer displayOrder;

    private Boolean isCover;
}