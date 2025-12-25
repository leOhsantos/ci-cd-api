package com.api.domain.repository;

import com.api.domain.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    List<User> findAll();
    Optional<User> findById(UUID id);
    User save(User newUser);
    void delete(UUID id);
    boolean existsByEmail(String email);
}