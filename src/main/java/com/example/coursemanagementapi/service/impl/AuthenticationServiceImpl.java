package com.example.coursemanagementapi.service.impl;

import com.example.coursemanagementapi.core.utils.JwtUtils;
import com.example.coursemanagementapi.dto.LoginRequest;
import com.example.coursemanagementapi.dto.SignUpRequest;
import com.example.coursemanagementapi.dto.response.TokenResponse;
import com.example.coursemanagementapi.enums.Role;
import com.example.coursemanagementapi.persistence.entities.User;
import com.example.coursemanagementapi.persistence.repository.UserRepository;
import com.example.coursemanagementapi.service.AuthenticationService;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Value("${app.expiration:60000}")
    private int jwtExpirationMs;

    public User signUp(SignUpRequest signUpRequest) {
        User user = new User();
        user.setFirstname(signUpRequest.getFirstName());
        user.setLastname(signUpRequest.getLastName());
        user.setEmail(signUpRequest.getEmail());
        user.setStatus(signUpRequest.isStatus());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword().trim()));
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    @Override
    public TokenResponse authenticatedUser(LoginRequest loginRequest) {
        User user;
        if (loginRequest.getUsername().contains("@")) {
            user = userRepository.findByEmail(loginRequest.getUsername()).orElseThrow(() -> new NoResultException("Invalid username or password"));
        } else {
            user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new NoResultException("Invalid username or password"));
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        String accessToken = jwtUtils.generateToken(loginRequest.getUsername());
        String refreshToken = jwtUtils.generateRefreshToken(loginRequest.getUsername());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        return TokenResponse.builder().accessToken(accessToken).refreshToken(refreshToken).expires_in(jwtExpirationMs).build();
    }
}
