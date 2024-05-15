package com.example.coursemanagementapi.persistence.repository;

import com.example.coursemanagementapi.persistence.entities.Course;
import com.example.coursemanagementapi.dto.response.CourseResponse;
import com.example.coursemanagementapi.persistence.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findById(Long id);

    Optional<Course> findByCategory_Id(Long id);

    Page<Course> findAllByUsersIn(List<User> users, Pageable pageable);
}
