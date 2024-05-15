package com.example.coursemanagementapi.service.impl;

import com.example.coursemanagementapi.persistence.entities.User;
import com.example.coursemanagementapi.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user;
        if (email.contains("@")) {
            user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Incorrect username or password"));
        } else {
            user = userRepository.findByUsername(email).orElseThrow(() -> new UsernameNotFoundException("Incorrect username or password"));
        }

        if (!user.isStatus()) {
            throw new DisabledException("User is deactivated. Please contact the administrator.");
        }
        user.setLastActiveTime(new Timestamp(System.currentTimeMillis()));
        userRepository.save(user);
        return user;
    }
}
