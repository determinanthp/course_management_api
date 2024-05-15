package com.example.coursemanagementapi.persistence.repository;

import com.example.coursemanagementapi.persistence.entities.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    Optional<Lesson> findByTitle(String title);
}
