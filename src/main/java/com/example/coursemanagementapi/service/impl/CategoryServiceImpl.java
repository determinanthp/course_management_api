package com.example.coursemanagementapi.service.impl;

import com.example.coursemanagementapi.core.utils.ModelMapperUtils;
import com.example.coursemanagementapi.dto.request.CategoryRequest;
import com.example.coursemanagementapi.dto.response.CategoryResponse;
import com.example.coursemanagementapi.persistence.entities.Category;
import com.example.coursemanagementapi.persistence.repository.CategoryRepository;
import com.example.coursemanagementapi.service.CategoryService;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = categoryRepository.findByTitle(request.getTitle()).orElseThrow(() -> new RuntimeException("Category not found"));
        return ModelMapperUtils.map(category, CategoryResponse.class);
    }

    @Override
    public CategoryResponse updateCategory(CategoryRequest request, Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NoResultException("Category not existed"));
        if (request.getDescription() != null) {
            category.setDescription(request.getDescription());
        }

        if (request.getTitle() != null) {
            category.setTitle(request.getTitle());
        }

        category = categoryRepository.save(category);
        return ModelMapperUtils.map(category, CategoryResponse.class);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.findById(id).ifPresent(categoryRepository::delete);
    }

    @Override
    public Page<CategoryResponse> findAll(int size, int limit) {
        Page<Category> pageable = categoryRepository.findAll(PageRequest.of(size, limit, Sort.by(Sort.Direction.DESC, "createdAt")));
        return ModelMapperUtils.mapAllPage(pageable, CategoryResponse.class);
    }
}
