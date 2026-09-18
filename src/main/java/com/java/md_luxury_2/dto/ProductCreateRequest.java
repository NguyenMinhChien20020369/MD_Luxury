package com.java.md_luxury_2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProductCreateRequest(
        @NotBlank(message = "SKU không được để trống")
        @Size(max = 50, message = "SKU tối đa 50 ký tự")
        String sku,

        @NotBlank(message = "Tên sản phẩm không được để trống")
        @Size(max = 200, message = "Tên sản phẩm tối đa 200 ký tự")
        String name,

        String description
) {}