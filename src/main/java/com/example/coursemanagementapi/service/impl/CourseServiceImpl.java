package com.example.coursemanagementapi.service.impl;

import com.example.coursemanagementapi.core.utils.ModelMapperUtils;
import com.example.coursemanagementapi.dto.request.CoursePage;
import com.example.coursemanagementapi.dto.request.CourseRequest;
import com.example.coursemanagementapi.dto.response.CourseResponse;
import com.example.coursemanagementapi.enums.Role;
import com.example.coursemanagementapi.persistence.entities.Category;
import com.example.coursemanagementapi.persistence.entities.Course;
import com.example.coursemanagementapi.persistence.entities.Payment;
import com.example.coursemanagementapi.persistence.entities.User;
import com.example.coursemanagementapi.persistence.repository.CategoryRepository;
import com.example.coursemanagementapi.persistence.repository.CourseRepository;
import com.example.coursemanagementapi.persistence.repository.PaymentRepository;
import com.example.coursemanagementapi.persistence.repository.UserRepository;
import com.example.coursemanagementapi.service.CourseService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public CourseResponse createCourse(CourseRequest request) {
        User user = userRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new NoResultException("Instructor not found"));

        // validate user role
        if (!user.getRole().equals(Role.ROLE_INSTRUCTOR)) {
            throw new RuntimeException("You are not allowed to create a course");
        } else if (!user.getRole().equals(Role.SUPER_ADMIN)) {
        }

        // Find the category by Id from the request
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        Course course = Course.builder()
                .title(request.getTitle())
                .category(category)
                .description(request.getDescription())
                .price(BigDecimal.ZERO)
                .build();
        course.addUser(user);
        course = courseRepository.save(course);

        return ModelMapperUtils.map(course, CourseResponse.class);
    }

    @Override
    @Transactional
    public CourseResponse updateCourse(CourseRequest request, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NoResultException("Course not found"));

        if (request.getTitle() != null) {
            course.setTitle(request.getTitle());
        }
        if (request.getPrice() != null) {
            course.setPrice(request.getPrice());
        }
        if (request.getDescription() != null) {
            course.setDescription(request.getDescription());
        }
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new NoResultException("Category cannot be found"));
            course.setCategory(category);
        }
        course = courseRepository.save(course);

        return ModelMapperUtils.map(course, CourseResponse.class);

    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.findById(id).ifPresent(courseRepository::delete);
    }

    @Override
    public Page<CourseResponse> findAllCourses(CoursePage coursePage) {
        Page<Course> pageable = null;
        User user = userRepository.findByUsername(coursePage.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getRole().equals(Role.SUPER_ADMIN) || user.getRole().equals(Role.ADMIN)) {
            pageable = courseRepository.findAll(PageRequest.of(coursePage.getSize(), coursePage.getLimit(), Sort.by(Sort.Direction.DESC, "createdAt")));
        } else if (user.getRole().equals(Role.ROLE_INSTRUCTOR) || user.getRole().equals(Role.USER)) {
            pageable = courseRepository.findAllByUsersIn(List.of(user), PageRequest.of(coursePage.getSize(), coursePage.getLimit(), Sort.by(Sort.Direction.DESC, "createdAt")));
        } else {
            throw new RuntimeException("You are not allowed to view course(s)");
        }
        return ModelMapperUtils.mapAllPage(pageable, CourseResponse.class);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public boolean subscribeToCourse(Long userId, Long courseId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
        Payment payment = paymentRepository.findByUserAndCourse(user, course).orElseThrow(() -> new RuntimeException("Payment not found for this course"));
        payment = paymentRepository.save(payment);
        course.getUsers().add(user);
        course = courseRepository.save(course);
        return course.getUsers().contains(user);
    }
}
