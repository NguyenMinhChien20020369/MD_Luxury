package com.java.md_luxury_2.controller.publicapi;

import com.java.md_luxury_2.dto.CategoryPublicDTO;
import com.java.md_luxury_2.service.CategoryPublicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/v1/public/categories")
public class CategoryPublicController {

    private final CategoryPublicService service;

    public CategoryPublicController(CategoryPublicService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CategoryPublicDTO>> list() {
        return ResponseEntity.ok(service.listAll());
    }
}