package com.example.coursemanagementapi.controller;

import com.example.coursemanagementapi.persistence.entities.User;
import com.example.coursemanagementapi.dto.request.UserRequest;
import com.example.coursemanagementapi.dto.response.UserResponse;
import com.example.coursemanagementapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest){
        return new ResponseEntity(userService.createUser(userRequest), HttpStatus.CREATED);
    }
    @PutMapping("/id")
    public ResponseEntity<UserResponse> updateUser(@PathVariable (value = "id") Long id,@RequestBody UserRequest request){
        return new ResponseEntity(userService.updateUser(request, id), HttpStatus.OK);
    }
    @DeleteMapping("/id")
    public ResponseEntity<Void> deleteUser(@PathVariable(value = "userId") Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
  /*  @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser(@RequestBody User currentUser){
        return ResponseEntity.ok(currentUser);
    }*/
    @GetMapping
    public List<User> findAllUsers(){
        return userService.findAllUsers();
    }
    @GetMapping("paginate")
    public ResponseEntity<UserResponse> findAllUsersPaginate(@PathVariable int size, int limit) {
        return new ResponseEntity(userService.findAllUsersPaginate(size, limit), HttpStatus.OK);
    }
}
