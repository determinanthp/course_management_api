package com.example.coursemanagementapi.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Super;

@Entity
@Table(name = "lessons")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Lesson extends BaseEntity {
    private String title;
    @ManyToOne
    private Media media;
    @ManyToOne
    @JoinColumn(name = "coursemodule_id")
    private CourseModule courseModule;

}
