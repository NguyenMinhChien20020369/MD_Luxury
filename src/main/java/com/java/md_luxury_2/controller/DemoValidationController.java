package com.java.md_luxury_2.controller;

import com.java.md_luxury_2.dto.ContactRequest;
import com.java.md_luxury_2.dto.ProductAdminDTO;
import com.java.md_luxury_2.dto.ProductPublicDTO;
import com.java.md_luxury_2.entity.Product;
import com.java.md_luxury_2.entity.ProductStatus;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/demo")
public class DemoValidationController {

    // API 1: Test validation từ Request body (@Valid)
    @PostMapping("/contact")
    public ResponseEntity<Map<String, Object>> testContactValidation(@Valid @RequestBody ContactRequest request) {
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Dữ liệu gửi lên hợp lệ!",
                "data", request
        ));
    }

    // API 2: Test trả về Public DTO (không lộ thông tin quản trị)
    @GetMapping("/product/public")
    public ProductPublicDTO getPublicProductDemo() {
        Product mockProduct = createMockProduct();
        return ProductPublicDTO.from(mockProduct, "https://cdn.mdluxury.vn/images/cover.jpg");
    }

    // API 3: Test trả về Admin DTO (có thông tin status, createdAt, updatedAt)
    @GetMapping("/product/admin")
    public ProductAdminDTO getAdminProductDemo() {
        Product mockProduct = createMockProduct();
        return ProductAdminDTO.from(mockProduct);
    }

    // API 4: Test ném lỗi nghiệp vụ để kiểm tra GlobalExceptionHandler
    @PostMapping("/product/publish-fail")
    public ResponseEntity<Void> testExceptionHandling() {
        throw new IllegalStateException("Chỉ xuất bản được sản phẩm đang ở trạng thái PENDING");
    }

    private Product createMockProduct() {
        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setSku("BAG-HERMES-001");
        product.setName("Túi xách Luxury");
        product.setSlug("tui-xach-luxury");
        product.setDescription("Mô tả chi tiết túi xách cao cấp");
        product.setStatus(ProductStatus.DRAFT);
        product.setCreatedAt(OffsetDateTime.now());
        product.setUpdatedAt(OffsetDateTime.now());
        return product;
    }
}