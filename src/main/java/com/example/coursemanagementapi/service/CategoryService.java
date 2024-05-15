package com.example.coursemanagementapi.service;

import com.example.coursemanagementapi.dto.request.CategoryRequest;
import com.example.coursemanagementapi.dto.response.CategoryResponse;
import org.springframework.data.domain.Page;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse updateCategory(CategoryRequest request, Long id);

    void deleteCategory(Long id);

    Page<CategoryResponse> findAll(int size, int limit);
}
