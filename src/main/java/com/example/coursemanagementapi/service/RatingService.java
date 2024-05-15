package com.example.coursemanagementapi.service;

import com.example.coursemanagementapi.persistence.entities.Rating;
import org.apache.coyote.Response;

public interface RatingService {
    public Rating addRatingToCourse(Long id, int ratingValue);
    public Double calculateAverageRating(Long id);
}
