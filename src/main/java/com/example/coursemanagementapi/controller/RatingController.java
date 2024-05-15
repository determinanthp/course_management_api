package com.example.coursemanagementapi.controller;

import com.example.coursemanagementapi.persistence.entities.Rating;
import com.example.coursemanagementapi.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rating")
public class RatingController {
    private final RatingService ratingService;
    @GetMapping
    @PreAuthorize("hasRole('User')")
    public Rating addRatingToCourse(@PathVariable (value = "id") Long id, int ratingValue){
        return ratingService.addRatingToCourse(id, ratingValue);
    }

    @GetMapping("/id")
    public  Double calculateAverageRating(@PathVariable Long id){
        return ratingService.calculateAverageRating(id);
    }
}
