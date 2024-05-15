package com.example.coursemanagementapi.service.impl;

import com.example.coursemanagementapi.core.utils.ModelMapperUtils;
import com.example.coursemanagementapi.dto.request.UserRequest;
import com.example.coursemanagementapi.dto.response.UserResponse;
import com.example.coursemanagementapi.persistence.entities.User;
import com.example.coursemanagementapi.persistence.repository.UserRepository;
import com.example.coursemanagementapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(UserRequest userRequest) {
        userRepository.findByEmailOrUsername(userRequest.getEmail(), userRequest.getUserName()).ifPresent((user) -> {
            throw new RuntimeException("User already exist");
        });
        return userRepository.save(User.builder()
                .email(userRequest.getEmail())
                .username(userRequest.getUserName())
                .firstname(userRequest.getFirstName())
                .mobileNo(userRequest.getMobileNo())
                .role(userRequest.getRole())
                .password(passwordEncoder.encode("password"))
                .build());
    }

    @Override
    public User updateUser(UserRequest request, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        if (request.getRole() != null) {
            user.setRole(request.getRole());
        }
        if (request.getFirstName() != null) {
            user.setFirstname(request.getFirstName());
        }
        if (request.getLastName() != null) {
            user.setLastname(request.getLastName());
        }
        if (request.getMobileNo() != null) {
            user.setMobileNo(request.getMobileNo());
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresent(userRepository::delete);
    }

    /*@Override
    public ResponseEntity<User> authenticatedUser() {
        //retrieves the current Authentication object from the SecurityContextHolder.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return ResponseEntity.ok(currentUser);
    }*/

    @Override
    public List<User> findAllUsers() {
        //transfers all users from the Iterable to the List.
        return userRepository.findAll().stream()
                .collect(Collectors.toList());
    }

    @Override
    public Page<UserResponse> findAllUsersPaginate(int size, int limit) {
        Page<User> pageable = userRepository.findAll(PageRequest.of(size, limit, Sort.by(Sort.Direction.DESC, "createdAt")));
        return ModelMapperUtils.mapAllPage(pageable, UserResponse.class);
    }
}
