package com.example.coursemanagementapi.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MediaRequest {
    private String filename;
    private Integer fileSize;
    private String fileType;
}
