package com.example.coursemanagementapi.controller;

import com.example.coursemanagementapi.dto.request.LessonRequest;
import com.example.coursemanagementapi.dto.response.LessonResponse;
import com.example.coursemanagementapi.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class LessonController {

    private LessonService lessonService;
    @PostMapping
    public ResponseEntity<LessonResponse> createLesson(@RequestBody LessonRequest request){
        return new ResponseEntity<>(lessonService.createLesson(request), HttpStatus.OK);
    }
    @PutMapping("/id")
    public ResponseEntity<LessonResponse> updateLesson(@PathVariable (value = "id") Long id, @RequestBody LessonRequest lessonRequest){
        return new ResponseEntity<>(lessonService.updateLesson(lessonRequest, id), HttpStatus.OK);
    }
    @DeleteMapping("/id")
    public ResponseEntity<Void> deleteLesson(Long id){
        lessonService.deleteLesson(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public final ResponseEntity<LessonResponse>findAll(@PathVariable ("id") int size, int limit){
        return new ResponseEntity(lessonService.findAll(size, limit), HttpStatus.OK);
    }
}
