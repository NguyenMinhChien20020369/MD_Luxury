package com.java.md_luxury_2.dto;

import jakarta.validation.constraints.NotBlank;

public record NoteRequest(
        @NotBlank(message = "Nội dung ghi chú không được để trống")
        String content
) {}