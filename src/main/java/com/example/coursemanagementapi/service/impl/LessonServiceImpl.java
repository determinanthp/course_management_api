package com.example.coursemanagementapi.service.impl;

import com.example.coursemanagementapi.core.exception.CustomException;
import com.example.coursemanagementapi.core.utils.ModelMapperUtils;
import com.example.coursemanagementapi.dto.request.LessonRequest;
import com.example.coursemanagementapi.dto.response.LessonResponse;
import com.example.coursemanagementapi.persistence.entities.CourseModule;
import com.example.coursemanagementapi.persistence.entities.Lesson;
import com.example.coursemanagementapi.persistence.entities.Media;
import com.example.coursemanagementapi.persistence.repository.CourseModuleRepository;
import com.example.coursemanagementapi.persistence.repository.LessonRepository;
import com.example.coursemanagementapi.service.LessonService;
import com.example.coursemanagementapi.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    private final CourseModuleRepository moduleRepository;
    private final MediaService mediaService;

    @Override
    public LessonResponse createLesson(LessonRequest request) {
        lessonRepository.findByTitle(request.getTitle()).ifPresent((less) -> {
            throw new RuntimeException(String.format("Lesson with title %s already exist", request.getTitle()));
        });

        Media media = mediaService.createMedia(request.getFile());

        Lesson lesson = lessonRepository.save(Lesson.builder()
                .title(request.getTitle())
                .media(media)
                .build());
        return ModelMapperUtils.map(lesson, LessonResponse.class);
    }

    @Override
    public LessonResponse updateLesson(LessonRequest request, Long id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new RuntimeException("lesson not found"));
        if (request.getTitle() != null) {
            lesson.setTitle(request.getTitle());
        }
        if (request.getFile() != null) {
            Media media = mediaService.createMedia(request.getFile());
            lesson.setMedia(media);
        }
        if (request.getModuleId() != null) {
            lesson.setCourseModule(moduleRepository.findById(request.getModuleId()).orElseThrow(() -> new RuntimeException("Module not found")));
        }
        lesson = lessonRepository.save(lesson);
        return ModelMapperUtils.map(lesson, LessonResponse.class);
    }

    @Override
    public void deleteLesson(Long id) {
        lessonRepository.findById(id).ifPresent(lessonRepository::delete);
    }

    @Override
    public Page<LessonResponse> findAll(int size, int limit) {
        Page<Lesson> pageable = lessonRepository.findAll(PageRequest.of(size, limit, Sort.by(Sort.Direction.DESC, "createdAt")));
        return ModelMapperUtils.mapAllPage(pageable, LessonResponse.class);
    }
}
