package com.java.md_luxury_2.security;

import java.util.UUID;

public record CurrentUser(
        UUID userId,
        String role
) {}