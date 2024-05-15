package com.example.coursemanagementapi.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "courses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Course extends BaseEntity {
    private String title;
    private BigDecimal price;
    private String description;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToMany
    private Set<User> users;
    @OneToMany
    private Set<Rating> ratings;
    @OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<CourseModule> courseModules;

    public Set<User> addUser(User user) {
        if (this.getUsers().isEmpty()) {
            this.setUsers(Set.of(user));
        } else {
            this.getUsers().add(user);
        }
        return this.getUsers();
    }

    public void setRatings(double v) {
    }
}
