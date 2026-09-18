package com.java.md_luxury_2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class PingController {

    // API mẫu trong bài học: GET http://localhost:8080/api/v1/ping
    @GetMapping("/ping")
    public Map<String, Object> ping() {
        return Map.of(
                "status", "ok",
                "time", LocalDateTime.now()
        );
    }

    // ===== BÀI TẬP: API GET http://localhost:8080/api/v1/hello/{name} =====
    @GetMapping("/hello/{name}")
    public Map<String, String> hello(@PathVariable String name) {
        return Map.of("message", "Xin chào, " + name);
    }
}