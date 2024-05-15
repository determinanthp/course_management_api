package com.example.coursemanagementapi.service.impl;

import com.example.coursemanagementapi.core.utils.ModelMapperUtils;
import com.example.coursemanagementapi.dto.request.CourseModuleRequest;
import com.example.coursemanagementapi.dto.response.CourseModuleResponse;
import com.example.coursemanagementapi.persistence.entities.Course;
import com.example.coursemanagementapi.persistence.entities.CourseModule;
import com.example.coursemanagementapi.persistence.repository.CourseRepository;
import com.example.coursemanagementapi.persistence.repository.CourseModuleRepository;
import com.example.coursemanagementapi.service.CourseModuleService;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseModuleServiceImpl implements CourseModuleService {

    private final CourseModuleRepository moduleRepository;
    private final CourseRepository courseRepository;

    @Override
    public CourseModuleResponse createModule(CourseModuleRequest moduleRequest) {
        moduleRepository.findByTitle(moduleRequest.getTitle()).ifPresent((m) -> {
            throw new NoResultException("Module already exist");
        });
        Course course = courseRepository.findById(moduleRequest.getCourseId()).orElseThrow(() -> new RuntimeException("Course not found"));
        CourseModule module = moduleRepository.save(CourseModule.builder()
                .title(moduleRequest.getTitle())
                .description(moduleRequest.getDescription())
                .course(course)
                .build());
        return ModelMapperUtils.map(module, CourseModuleResponse.class);
    }

    @Override
    public CourseModuleResponse updateModule(Long id, CourseModuleRequest request) {
        CourseModule module = moduleRepository.findById(id).orElseThrow(() -> new RuntimeException("Module not found"));
        if (request.getDescription() != null) {
            module.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            module.setDescription(request.getDescription());
        }
        if (request.getCourseId() != null) {
            module.setCourse(courseRepository.findById(request.getCourseId()).orElseThrow(() -> new RuntimeException("Course not found")));
        }
        module = moduleRepository.save(module);

        return ModelMapperUtils.map(module, CourseModuleResponse.class);
    }

    @Override
    public Page<CourseModuleResponse> getRatedCourses() {
        List<Course> allCourses = courseRepository.findAll();
        // Update ratings for each course
        for (Course course : allCourses) {
            // Assuming the method countByCourseAndCompletionStatus and countByCourse
            // take Course object or its ID. Replace `Course` with the appropriate method argument.
            int completedCourseModules = moduleRepository.countByCourseAndCompletionStatus(course, true);
            int totalCourseModules = moduleRepository.countByCourse(course);

            if (totalCourseModules > 0) {  // Prevent division by zero
                double completionPercentage = (double) completedCourseModules / totalCourseModules;
                course.setRatings(completionPercentage * 100);  // Set ratings based on completion percentage
            } else {
                course.setRatings(0);  // Handle cases where there are no modules
            }
        }
        return null;
    }

    @Override
    public boolean deleteModule(Long id) {
        moduleRepository.findById(id).ifPresent(moduleRepository::delete);
        return false;
    }

    @Override
    public Page<CourseModuleResponse> findAllModules(int size, int limit) {
        Page<CourseModule> pageable = moduleRepository.findAll(PageRequest.of(size, limit, Sort.by(Sort.Direction.DESC, "createdAt")));
        return ModelMapperUtils.mapAllPage(pageable, CourseModuleResponse.class);
    }
}