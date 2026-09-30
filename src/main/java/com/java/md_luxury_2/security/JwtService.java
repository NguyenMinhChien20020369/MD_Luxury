package com.java.md_luxury_2.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtService {

    // Khoá bí mật dùng ký HMAC SHA (Tối thiểu 32 ký tự). Trong thực tế nên đưa vào application.yml / Secrets Manager
    private static final String SECRET = "day-la-khoa-bi-mat-toi-thieu-32-ky-tu-cho-hmac-md-luxury";
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public String generateToken(UUID userId, String role) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + 30 * 60 * 1000); // Token có thời hạn 30 phút

        return Jwts.builder()
                .subject(userId.toString())
                .claim("role", role)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(key)
                .compact();
    }

    public boolean isValid(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (SignatureException e) {
            throw new RuntimeException("Chữ ký Token không hợp lệ (Invalid JWT signature)");
        } catch (MalformedJwtException e) {
            throw new RuntimeException("Định dạng Token không hợp lệ (Invalid JWT token)");
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token đã hết hạn sử dụng (Expired JWT token)");
        } catch (UnsupportedJwtException e) {
            throw new RuntimeException("Token không được hỗ trợ (Unsupported JWT token)");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Chuỗi Token trống hoặc rỗng (JWT claims string is empty)");
        } catch (Exception e) {
            return false;
        }
    }

    public Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}