package com.example.coursemanagementapi.service.impl;

import com.example.coursemanagementapi.dto.request.PaymentRequest;
import com.example.coursemanagementapi.service.CourseService;
import com.example.coursemanagementapi.service.PaymentService;
import com.example.coursemanagementapi.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final PaymentService paymentService;
    private final CourseService courseService;

    @Override
    public boolean subscribe(PaymentRequest paymentRequest) {
        paymentService.makePayment(paymentRequest);
        boolean subscribeToCourse = courseService.subscribeToCourse(paymentRequest.getUserId(), paymentRequest.getCourseId());
        return subscribeToCourse;
    }
}
