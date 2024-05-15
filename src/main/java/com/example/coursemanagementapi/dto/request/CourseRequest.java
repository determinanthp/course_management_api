package com.example.coursemanagementapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CourseRequest {
    private String title;
    private BigDecimal price;
    private String description;
    private Long categoryId;
    private String rank;
    private Long authorId;
}
