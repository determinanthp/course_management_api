package com.example.coursemanagementapi.controller;

import com.example.coursemanagementapi.dto.request.PaymentRequest;
import com.example.coursemanagementapi.persistence.entities.Payment;
import com.example.coursemanagementapi.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public Payment makePayment(@RequestBody PaymentRequest paymentRequest){
        return paymentService.makePayment(paymentRequest);
    }
}
