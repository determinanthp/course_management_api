package com.example.coursemanagementapi.controller;

import com.example.coursemanagementapi.persistence.entities.Course;
import com.example.coursemanagementapi.dto.request.CourseRequest;
import com.example.coursemanagementapi.dto.response.CourseResponse;
import com.example.coursemanagementapi.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    @PostMapping
    @PreAuthorize("hasAnyRole('Instructor', 'Admin')")
    public ResponseEntity<CourseResponse> createCourse(@RequestBody CourseRequest courseRequest){
        return new ResponseEntity<>(courseService.createCourse(courseRequest), HttpStatus.CREATED);
    }

    @PutMapping("/id")
    @PreAuthorize("hasRole('Instructor')")
    public ResponseEntity<CourseResponse> updateCourse(@PathVariable (value = "id") long id, @RequestBody CourseRequest request){
        return new ResponseEntity<>(courseService.updateCourse(request, id), HttpStatus.OK);
    }
    @GetMapping("/delete")
    @PreAuthorize("hasRole('super admin')")
    public ResponseEntity<Void> deleteCourse(@PathVariable (value = "id") Long id){
        courseService.deleteCourse(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public List<Course> getAllCourses(){
        return new ArrayList<Course>(courseService.getAllCourses());
    }
}
