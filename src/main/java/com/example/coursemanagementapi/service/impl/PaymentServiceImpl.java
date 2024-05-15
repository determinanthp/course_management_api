package com.example.coursemanagementapi.service.impl;


import com.example.coursemanagementapi.dto.request.PaymentRequest;
import com.example.coursemanagementapi.persistence.entities.Course;
import com.example.coursemanagementapi.persistence.entities.Payment;
import com.example.coursemanagementapi.persistence.entities.User;
import com.example.coursemanagementapi.persistence.repository.CourseRepository;
import com.example.coursemanagementapi.persistence.repository.PaymentRepository;
import com.example.coursemanagementapi.persistence.repository.UserRepository;
import com.example.coursemanagementapi.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Override
    public Payment makePayment(PaymentRequest request) {
        Course course = courseRepository.findById(request.getCourseId()).orElseThrow(() -> new RuntimeException("Course does not exist"));
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        Payment payment = Payment.builder().amount(request.getAmount()).course(course).user(user).build();
        payment = paymentRepository.save(payment);
        return payment;
    }
}
