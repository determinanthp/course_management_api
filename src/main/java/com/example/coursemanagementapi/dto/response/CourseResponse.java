package com.example.coursemanagementapi.dto.response;

import com.example.coursemanagementapi.persistence.entities.Course;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseResponse {
    private Long id;
    private String courseInfo;
    private String completionStatus;
    private String description;
    private String title;
    private String startDate;
    private String endDate;
    private String Category;
    private String currentEnrollment;


    public CourseResponse(Course course) {
    }
}
