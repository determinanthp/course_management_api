package com.example.coursemanagementapi.controller;

import com.example.coursemanagementapi.dto.request.CourseModuleRequest;
import com.example.coursemanagementapi.dto.response.CourseModuleResponse;
import com.example.coursemanagementapi.persistence.entities.Course;
import com.example.coursemanagementapi.service.CourseModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/course_modules")
public class CourseModuleController {

    private  final CourseModuleService moduleService;

    @PostMapping
    public ResponseEntity<CourseModuleResponse> createModule(@RequestBody CourseModuleRequest request){
        return new ResponseEntity<>(moduleService.createModule(request), HttpStatus.CREATED);
    }

    @PutMapping("/id")
    public ResponseEntity<CourseModuleResponse> updateModule(@PathVariable (value = "id") Long id, @RequestBody CourseModuleRequest request){
        return new ResponseEntity<>(moduleService.updateModule(id, request), HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<Void> deleteModule(@PathVariable (value = "id") Long id){
        moduleService.deleteModule(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public final ResponseEntity<CourseModuleResponse>findAllModules(@PathVariable ("id") int size, int limit){
        return new ResponseEntity(moduleService.findAllModules(size, limit), HttpStatus.OK);
    }
}
