package com.example.coursemanagementapi.service;

import com.example.coursemanagementapi.persistence.entities.User;
import com.example.coursemanagementapi.dto.request.UserRequest;
import com.example.coursemanagementapi.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    public User createUser(UserRequest userRequest);
    public User updateUser(UserRequest request, Long id);
    public void deleteUser(Long userId);
 //   public ResponseEntity<User> authenticatedUser();
    public List<User> findAllUsers();
    public Page<UserResponse> findAllUsersPaginate(int size, int limit);
}
