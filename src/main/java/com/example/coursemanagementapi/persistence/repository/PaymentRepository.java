package com.example.coursemanagementapi.persistence.repository;

import com.example.coursemanagementapi.persistence.entities.Course;
import com.example.coursemanagementapi.persistence.entities.Payment;
import com.example.coursemanagementapi.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByUserAndCourse(User user, Course course);
}
