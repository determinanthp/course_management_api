package com.example.coursemanagementapi.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "course_modules")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CourseModule extends BaseEntity {
    private String title;
    private String description;
    private boolean completionStatus;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    @OneToMany
    private Set<Lesson> lessons;
    @OneToMany
    private Set<Media> media;
}
