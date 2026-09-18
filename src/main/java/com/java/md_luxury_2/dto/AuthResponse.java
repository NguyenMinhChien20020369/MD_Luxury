package com.java.md_luxury_2.dto;

public record AuthResponse(
        String accessToken,
        String tokenType
) {
    public AuthResponse(String accessToken) {
        this(accessToken, "Bearer");
    }
}