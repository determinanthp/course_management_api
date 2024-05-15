package com.example.coursemanagementapi.service;

import com.example.coursemanagementapi.dto.request.PaymentRequest;

public interface SubscriptionService {
    public boolean subscribe(PaymentRequest paymentRequest);
}
