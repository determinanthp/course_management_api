package com.example.coursemanagementapi.dto.response;

import com.example.coursemanagementapi.enums.Role;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String mobileNo;
    private Role role;
}
