package com.java.md_luxury_2.service;

import com.java.md_luxury_2.dto.ProductAdminDTO;
import com.java.md_luxury_2.dto.ProductCreateRequest;
import com.java.md_luxury_2.dto.ProductUpdateRequest;
import com.java.md_luxury_2.entity.Product;
import com.java.md_luxury_2.entity.ProductStatus;
import com.java.md_luxury_2.repository.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class ProductCmsService {

    private final ProductRepository productRepository;

    public ProductCmsService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductAdminDTO create(ProductCreateRequest request, UUID currentUserId) {
        Product product = new Product();
        product.setSku(request.sku());
        product.setName(request.name());
        product.setSlug(toSlug(request.name()));
        product.setDescription(request.description());
        product.setStatus(ProductStatus.DRAFT);
        product.setCreatedBy(currentUserId);
        product.setCreatedAt(OffsetDateTime.now());
        product.setUpdatedAt(OffsetDateTime.now());

        Product saved = productRepository.save(product);
        return ProductAdminDTO.from(saved);
    }

    @Transactional
    @CacheEvict(value = "productDetail", key = "#result.slug")
    public ProductAdminDTO update(UUID id, ProductUpdateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sản phẩm với ID: " + id));

        // Kiểm tra logic theo yêu cầu bài tập Bài 9:
        // - DRAFT: sửa tự do
        // - PUBLISHED: sửa xong tự chuyển về PENDING để chờ duyệt lại
        if (product.getStatus() == ProductStatus.PUBLISHED) {
            product.setStatus(ProductStatus.PENDING);
        } else if (product.getStatus() != ProductStatus.DRAFT && product.getStatus() != ProductStatus.PENDING) {
            throw new IllegalStateException("Chỉ sản phẩm ở trạng thái DRAFT, PENDING hoặc PUBLISHED mới được sửa");
        }

        product.setName(request.name());
        product.setSlug(toSlug(request.name()));
        product.setDescription(request.description());
        product.setUpdatedAt(OffsetDateTime.now());

        Product updated = productRepository.save(product);
        return ProductAdminDTO.from(updated);
    }

    @Transactional
    // Xóa cache chi tiết sản phẩm theo slug khi gỡ xuất bản / unpublish
    @CacheEvict(value = "productDetail", key = "#product.slug")
    public void softDelete(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sản phẩm với ID: " + productId));

        product.setStatus(ProductStatus.UNPUBLISHED);
        product.setUpdatedAt(OffsetDateTime.now());
        productRepository.save(product);
    }

    private String toSlug(String name) {
        if (name == null) return "";

        // 1. Chuyển chuỗi sang chữ thường
        String str = name.toLowerCase();

        // 2. Tách các ký tự dấu ra khỏi chữ gốc (Ví dụ: "đ" -> "d", "á" -> "a")
        str = Normalizer.normalize(str, Normalizer.Form.NFD);

        // 3. Xóa các tổ hợp dấu vừa tách (Unicode Diacritical Marks)
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        str = pattern.matcher(str).replaceAll("");

        // 4. Thay thế chữ 'đ' và 'đ' hoa/thường (Normalizer không tự chuyển được chữ đ)
        str = str.replace('đ', 'd');

        // 5. Áp dụng logic cũ của bạn: Xóa ký tự đặc biệt và thay khoảng trắng thành dấu gạch ngang
        return str.replaceAll("[^a-z0-9\\s-]", "")
                .trim()
                .replaceAll("\\s+", "-");
    }

    @Transactional
    public ProductAdminDTO submit(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sản phẩm"));

        product.transitionTo(ProductStatus.PENDING);
        Product saved = productRepository.save(product);
        return ProductAdminDTO.from(saved);
    }

    @Transactional
    public ProductAdminDTO publish(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sản phẩm"));

        product.transitionTo(ProductStatus.PUBLISHED);
        Product saved = productRepository.save(product);
        return ProductAdminDTO.from(saved);
    }
}