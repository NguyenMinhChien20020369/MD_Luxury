package com.java.md_luxury_2.service;

import com.java.md_luxury_2.dto.ProductPublicDTO;
import com.java.md_luxury_2.entity.Product;
import com.java.md_luxury_2.entity.ProductImage;
import com.java.md_luxury_2.entity.ProductStatus;
import com.java.md_luxury_2.repository.ProductRepository;
import com.java.md_luxury_2.repository.ProductSpecifications;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductPublicService {

    private final ProductRepository productRepository;

    public ProductPublicService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<ProductPublicDTO> listPublished(String categorySlug, String sortBy, String direction, Pageable pageable) {
        // Bài tập Bài 7: Xử lý sắp xếp theo createdAt hoặc name
        Sort sort = Sort.unsorted();
        if ("createdAt".equalsIgnoreCase(sortBy)) {
            sort = "desc".equalsIgnoreCase(direction) ? Sort.by("createdAt").descending() : Sort.by("createdAt").ascending();
        } else if ("name".equalsIgnoreCase(sortBy)) {
            sort = "desc".equalsIgnoreCase(direction) ? Sort.by("name").descending() : Sort.by("name").ascending();
        }

        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                sort.and(pageable.getSort())
        );

        Specification<Product> spec = Specification.where(ProductSpecifications.isPublished())
                .and(ProductSpecifications.hasCategorySlug(categorySlug));

        return productRepository.findAll(spec, sortedPageable).map(this::toDTO);
    }

    @Cacheable(value = "productDetail", key = "#slug")
    public Optional<ProductPublicDTO> findPublishedBySlug(String slug) {
        return productRepository.findBySlugAndStatus(slug, ProductStatus.PUBLISHED)
                .map(this::toDTO);
    }

    private ProductPublicDTO toDTO(Product p) {
        String coverUrl = null;
        if (p.getImages() != null) {
            coverUrl = p.getImages().stream()
                    .filter(img -> Boolean.TRUE.equals(img.getIsCover()))
                    .findFirst()
                    .map(img -> img.getUrl())
                    .orElse(null);
        }

        return new ProductPublicDTO(
                p.getId().toString(),
                p.getName(),
                p.getSlug(),
                p.getDescription(),
                coverUrl
        );
    }
}