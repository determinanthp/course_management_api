package com.example.coursemanagementapi.persistence.repository;
import com.example.coursemanagementapi.enums.Role;
import com.example.coursemanagementapi.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query
    Optional<User> findByEmailOrUsername(String email, String username);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    User findByRole(Role role);
}
