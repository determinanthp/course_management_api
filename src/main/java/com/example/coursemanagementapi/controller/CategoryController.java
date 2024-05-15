package com.example.coursemanagementapi.controller;

import com.example.coursemanagementapi.persistence.entities.Category;
import com.example.coursemanagementapi.dto.request.CategoryRequest;
import com.example.coursemanagementapi.dto.response.CategoryResponse;
import com.example.coursemanagementapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest request){
        return new ResponseEntity<>(categoryService.createCategory(request), HttpStatus.CREATED);
    }

    @PutMapping("/id")
    public ResponseEntity<Category> updateCategory(@PathVariable (value = "id") Long id, @RequestBody CategoryRequest request){
        return new ResponseEntity(categoryService.updateCategory(request, id), HttpStatus.OK);
    }
    @GetMapping("/id")
    public ResponseEntity <Void> deleteCategory(@PathVariable (value = "id") Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public final ResponseEntity<CategoryResponse>findAll(@PathVariable ("id") int size, int limit){
        return new ResponseEntity(categoryService.findAll(size, limit), HttpStatus.OK);
    }
}
