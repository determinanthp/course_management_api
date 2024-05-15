package com.example.coursemanagementapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CategoryRequest {
    private Long id;
    private String description;
    private String title;


}
