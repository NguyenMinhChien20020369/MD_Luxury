package com.java.md_luxury_2.service;

import com.java.md_luxury_2.dto.CategoryPublicDTO;
import com.java.md_luxury_2.entity.Category;
import com.java.md_luxury_2.repository.CategoryRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryPublicService {

    private final CategoryRepository categoryRepository;

    public CategoryPublicService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Cacheable(value = "categories", key = "'all'")
    public List<CategoryPublicDTO> listAll() {
        return categoryRepository.findAllByOrderByDisplayOrderAsc()
                .stream()
                .map(c -> new CategoryPublicDTO(
                        c.getId().toString(),
                        c.getName(),
                        c.getSlug()
                ))
                .toList();
    }

    @CacheEvict(value = "categories", key = "'all'")
    public Category create(Category category) {
        // Xoá cache ngay khi có danh mục mới
        return categoryRepository.save(category);
    }
}