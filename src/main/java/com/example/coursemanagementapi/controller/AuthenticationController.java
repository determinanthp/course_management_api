package com.example.coursemanagementapi.controller;

import com.example.coursemanagementapi.dto.LoginRequest;
import com.example.coursemanagementapi.dto.SignUpRequest;
import com.example.coursemanagementapi.dto.response.TokenResponse;
import com.example.coursemanagementapi.persistence.entities.User;
import com.example.coursemanagementapi.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> authenticatedUser(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authenticationService.authenticatedUser(loginRequest));
    }
}
