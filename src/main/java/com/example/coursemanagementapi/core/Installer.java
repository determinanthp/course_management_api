package com.example.coursemanagementapi.core;

import com.example.coursemanagementapi.core.utils.JwtUtils;
import com.example.coursemanagementapi.enums.Role;
import com.example.coursemanagementapi.persistence.entities.User;
import com.example.coursemanagementapi.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class Installer implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    private Boolean alreadySetup = Boolean.FALSE;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        createUserIfNotExist("test");
        alreadySetup = true;
    }

    public User createUserIfNotExist(String username) {
        User user = userRepository.findByUsername(username).orElse(userRepository.save(User.builder()
                .username(username)
                .firstname("First")
                .lastname("Last")
                .email(username + "@example.com")
                .password(passwordEncoder.encode("password"))
                .role(Role.SUPER_ADMIN)
                .build()));
        String token = jwtUtils.generateToken("test");
        log.info("token: {}", token);
        return user;
    }
}
