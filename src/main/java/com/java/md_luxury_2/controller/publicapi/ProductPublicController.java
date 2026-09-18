package com.java.md_luxury_2.controller.publicapi;

import com.java.md_luxury_2.dto.ProductPublicDTO;
import com.java.md_luxury_2.service.ProductPublicService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/public/products")
public class ProductPublicController {

    private final ProductPublicService service;

    public ProductPublicController(ProductPublicService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<ProductPublicDTO>> list(
            @RequestParam(required = false) String categorySlug,
            @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String direction,
            Pageable pageable) {
        Page<ProductPublicDTO> page = service.listPublished(categorySlug, sortBy, direction, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ProductPublicDTO> detail(@PathVariable String slug) {
        Optional<ProductPublicDTO> product = service.findPublishedBySlug(slug);
        return product
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}