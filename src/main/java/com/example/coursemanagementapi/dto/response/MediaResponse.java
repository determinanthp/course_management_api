package com.example.coursemanagementapi.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MediaResponse {
    private Long id;
    private String filename;
    private Integer fileSize;
    private String fileType;
    private String extension;
    private String url;
}
