package com.example.coursemanagementapi.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseModuleRequest {
    private Long id;
    private String title;
    private String description;
    private Long courseId;
}
