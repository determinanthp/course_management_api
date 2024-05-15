package com.example.coursemanagementapi.persistence.repository;

import com.example.coursemanagementapi.persistence.entities.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media, Long> {

}
