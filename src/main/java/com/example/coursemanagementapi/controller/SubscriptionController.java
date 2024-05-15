package com.example.coursemanagementapi.controller;

import com.example.coursemanagementapi.dto.request.PaymentRequest;
import com.example.coursemanagementapi.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    public boolean subscribe(@RequestBody PaymentRequest paymentRequest){
        return subscriptionService.subscribe(paymentRequest);
    }
}
