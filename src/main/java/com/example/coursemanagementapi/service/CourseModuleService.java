package com.example.coursemanagementapi.service;

import com.example.coursemanagementapi.dto.request.CourseModuleRequest;
import com.example.coursemanagementapi.dto.response.CourseModuleResponse;
import org.springframework.data.domain.Page;

public interface CourseModuleService {
    public CourseModuleResponse createModule(CourseModuleRequest moduleRequest);

    public CourseModuleResponse updateModule(Long id, CourseModuleRequest request);

    public Page<CourseModuleResponse> getRatedCourses();

    public boolean deleteModule(Long id);

    Page<CourseModuleResponse> findAllModules(int size, int limit);
}
