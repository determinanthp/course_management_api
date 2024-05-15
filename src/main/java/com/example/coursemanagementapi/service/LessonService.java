package com.example.coursemanagementapi.service;

import com.example.coursemanagementapi.dto.request.LessonRequest;
import com.example.coursemanagementapi.dto.response.LessonResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface LessonService {
    public LessonResponse createLesson(LessonRequest request);
    public LessonResponse updateLesson(LessonRequest request, Long id);
    public void deleteLesson(Long id);
    public Page<LessonResponse> findAll(int size, int limit);
}
