package com.api.repository;

import com.api.domain.model.User;
import com.api.domain.repository.UserRepository;
import com.api.repository.jpa.UserJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;

    public List<User> findAll() {
        return userJpaRepository.findAll();
    }

    public Optional<User> findById(UUID id) {
        return userJpaRepository.findById(id);
    }

    public User save(User newUser) {
        return userJpaRepository.save(newUser);
    }

    public void delete(UUID id) {
        userJpaRepository.deleteById(id);
    }

    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }
}