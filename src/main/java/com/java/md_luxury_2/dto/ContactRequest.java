package com.java.md_luxury_2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ContactRequest(
        @NotBlank(message = "Vui lòng nhập họ tên")
        String fullName,

        @NotBlank(message = "Vui lòng nhập số điện thoại")
        @Pattern(regexp = "^(0|\\+84)[0-9]{9,10}$", message = "Số điện thoại không hợp lệ")
        String phone,

        String message,

        // Trường honeypot ẩn để chống spam bot
        String honeypot
) {}