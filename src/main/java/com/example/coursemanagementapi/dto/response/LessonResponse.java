package com.example.coursemanagementapi.dto.response;

import com.example.coursemanagementapi.persistence.entities.Media;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonResponse {
    private String title;

    private Media media;

    private Long module;
}
