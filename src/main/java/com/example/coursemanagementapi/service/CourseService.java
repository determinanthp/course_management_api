package com.example.coursemanagementapi.service;

import com.example.coursemanagementapi.dto.request.CoursePage;
import com.example.coursemanagementapi.persistence.entities.Course;
import com.example.coursemanagementapi.dto.request.CourseRequest;
import com.example.coursemanagementapi.dto.response.CourseResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CourseService {
    public CourseResponse createCourse(CourseRequest request);
    public CourseResponse updateCourse(CourseRequest request, Long courseId);
    void deleteCourse(Long id);
    List<Course> getAllCourses();
    Page<CourseResponse> findAllCourses(CoursePage coursePage);
    boolean subscribeToCourse(Long userId, Long courseId);
}
