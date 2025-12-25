package com.api.repository.jpa;

import com.api.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);
}