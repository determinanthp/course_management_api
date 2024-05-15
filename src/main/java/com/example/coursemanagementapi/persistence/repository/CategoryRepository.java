package com.example.coursemanagementapi.persistence.repository;

import com.example.coursemanagementapi.persistence.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByTitle(String title);
    Set<Category>findAllById(Long newCategoryIds);
}
