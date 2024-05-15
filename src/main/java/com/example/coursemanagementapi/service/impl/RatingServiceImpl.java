package com.example.coursemanagementapi.service.impl;

import com.example.coursemanagementapi.persistence.entities.Course;
import com.example.coursemanagementapi.persistence.entities.Rating;
import com.example.coursemanagementapi.persistence.repository.CourseRepository;
import com.example.coursemanagementapi.persistence.repository.RatingRepository;
import com.example.coursemanagementapi.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    private final CourseRepository courseRepository;
    @Override
    @Transactional
    public Rating addRatingToCourse(Long id, int ratingValue) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course ID: " + id));

        Rating rating = ratingRepository.findById(id).orElseThrow(()-> new RuntimeException("No rating found:" + id));
        rating.setRatingValue(ratingValue);
        rating.setCourse(course);
        return ratingRepository.save(rating);
    }

    @Override
    public Double calculateAverageRating(Long id) {
        List<Rating> ratings = ratingRepository.findByCourse_Id(id);
        return ratings.stream()
                .mapToInt(Rating::getRatingValue)
                .average()
                .orElseThrow(() -> new IllegalArgumentException("No ratings found for course ID: " + id));
    }
}
