package com.example.coursemanagementapi.dto.response;

import lombok.*;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CategoryResponse {
    private Long id;
    private String courseName;
    private String description;
    private String title;
    private Set<CourseResponse> courses;
    private String createdAt;
    private String modifiedAt;
}
