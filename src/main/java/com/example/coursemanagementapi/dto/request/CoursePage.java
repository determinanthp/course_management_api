package com.example.coursemanagementapi.dto.request;
import com.example.coursemanagementapi.enums.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoursePage {
    private int size;
    private int limit;
    private String username;
    private Role role;
}
