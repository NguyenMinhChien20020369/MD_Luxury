package com.java.md_luxury_2.repository;

import com.java.md_luxury_2.entity.Product;
import com.java.md_luxury_2.entity.ProductStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private ProductRepository productRepository;

    @Test
    void chiTraVeSanPhamDaXuatBan() {
        Product draft = new Product();
        draft.setStatus(ProductStatus.DRAFT);
        draft.setName("Sản phẩm nháp");
        productRepository.save(draft);

        Product published = new Product();
        published.setStatus(ProductStatus.PUBLISHED);
        published.setName("Sản phẩm đã xuất bản");
        productRepository.save(published);

        var result = productRepository.findByStatus(ProductStatus.PUBLISHED);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Sản phẩm đã xuất bản");
    }
}