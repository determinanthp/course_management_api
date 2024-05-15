package com.example.coursemanagementapi.service;

import com.example.coursemanagementapi.dto.request.PaymentRequest;
import com.example.coursemanagementapi.persistence.entities.Payment;

public interface PaymentService {

    Payment makePayment(PaymentRequest request);
}
