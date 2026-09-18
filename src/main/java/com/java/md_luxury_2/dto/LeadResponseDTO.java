package com.java.md_luxury_2.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record LeadResponseDTO(
        UUID id,
        String fullName,
        String phone,
        String message,
        String source,
        String status,
        LocalDateTime createdAt
) {}