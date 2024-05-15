package com.example.coursemanagementapi.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Category extends BaseEntity{
    private String courseName;
    private String description;
    private String title;
    @OneToMany(mappedBy = "category")
    private Set<Course> courses;

}
