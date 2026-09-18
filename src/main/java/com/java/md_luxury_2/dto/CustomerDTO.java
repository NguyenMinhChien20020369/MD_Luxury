package com.java.md_luxury_2.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CustomerDTO(
        UUID id,
        String fullName,
        String phoneEncrypted,
        String email,
        UUID ownerId,
        String tier,
        LocalDateTime createdAt
) {}