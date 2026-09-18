package com.java.md_luxury_2.service;

import com.java.md_luxury_2.dto.AuthResponse;
import com.java.md_luxury_2.dto.LoginRequest;
import com.java.md_luxury_2.entity.AppUser;
import com.java.md_luxury_2.repository.AppUserRepository;
import com.java.md_luxury_2.security.JwtService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(AppUserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse login(LoginRequest request) {
        AppUser user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new BadCredentialsException("Email hoặc mật khẩu không chính xác"));

        if (!user.isActive()) {
            throw new BadCredentialsException("Tài khoản đã bị khoá");
        }

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new BadCredentialsException("Email hoặc mật khẩu không chính xác");
        }

        String token = jwtService.generateToken(user.getId(), user.getRole());
        return new AuthResponse(token);
    }
}