package com.example.coursemanagementapi.service;

import com.example.coursemanagementapi.dto.LoginRequest;
import com.example.coursemanagementapi.dto.SignUpRequest;
import com.example.coursemanagementapi.dto.response.TokenResponse;
import com.example.coursemanagementapi.persistence.entities.User;

public interface AuthenticationService {
    User signUp(SignUpRequest signUpRequest);
    TokenResponse authenticatedUser(LoginRequest loginRequest);
}
