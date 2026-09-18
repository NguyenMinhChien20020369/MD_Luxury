package com.java.md_luxury_2.controller.cms;

import com.java.md_luxury_2.dto.ProductAdminDTO;
import com.java.md_luxury_2.dto.ProductCreateRequest;
import com.java.md_luxury_2.dto.ProductUpdateRequest;
import com.java.md_luxury_2.service.ProductCmsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cms/products")
public class ProductCmsController {

    private final ProductCmsService productCmsService;

    public ProductCmsController(ProductCmsService productCmsService) {
        this.productCmsService = productCmsService;
    }

    @PostMapping
    public ResponseEntity<ProductAdminDTO> create(@Valid @RequestBody ProductCreateRequest request) {
        // Tạm thời hardcode UUID của user tạo (sau Bài 12-13 sẽ lấy từ SecurityContext/JWT)
        UUID currentUserId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        ProductAdminDTO response = productCmsService.create(request, currentUserId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductAdminDTO> update(
            @PathVariable UUID id,
            @Valid @RequestBody ProductUpdateRequest request) {
        ProductAdminDTO response = productCmsService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable UUID id) {
        productCmsService.softDelete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/submit")
    @PreAuthorize("hasAnyRole('MARKETING', 'ADMIN')")
    public ResponseEntity<ProductAdminDTO> submit(@PathVariable UUID id) {
        return ResponseEntity.ok(productCmsService.submit(id));
    }

    @PutMapping("/{id}/publish")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductAdminDTO> publish(@PathVariable UUID id) {
        return ResponseEntity.ok(productCmsService.publish(id));
    }
}