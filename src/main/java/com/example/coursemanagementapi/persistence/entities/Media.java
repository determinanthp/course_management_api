package com.example.coursemanagementapi.persistence.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "media")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Media extends BaseEntity {
    private String filename;
    private Integer fileSize;
    private String contentType;
    private String extension;
    private String url;
}
