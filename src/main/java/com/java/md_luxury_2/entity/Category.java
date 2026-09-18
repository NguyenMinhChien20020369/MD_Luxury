package com.java.md_luxury_2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "category", schema = "content")
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String slug;
    private Integer displayOrder;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();
}