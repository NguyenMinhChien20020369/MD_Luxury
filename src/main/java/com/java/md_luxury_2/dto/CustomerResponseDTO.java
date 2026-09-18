package com.java.md_luxury_2.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CustomerResponseDTO(
        UUID id,
        String fullName,
        String phone,
        String email,
        UUID ownerId,
        String tier,
        LocalDateTime createdAt
) {}