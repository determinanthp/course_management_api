package com.example.coursemanagementapi.persistence.repository;

import com.example.coursemanagementapi.persistence.entities.Course;
import com.example.coursemanagementapi.persistence.entities.CourseModule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseModuleRepository extends JpaRepository<CourseModule, Long> {
    Optional<CourseModule> findByTitle(String title);
    Integer countByCourseAndCompletionStatus(Course course, boolean completionStatus);
    Integer countByCourse(Course course);

}
