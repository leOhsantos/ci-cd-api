package com.api.output.repository;

import com.api.domain.model.User;
import com.api.domain.repository.UserRepository;
import com.api.output.repository.jpa.entity.UserEntity;
import com.api.output.repository.jpa.repository.UserJpaRepository;
import com.api.output.repository.jpa.mapper.UserEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;
    private final UserEntityMapper mapper;

    @Override
    public List<User> findAll() {
        return userJpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userJpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = mapper.toEntity(user);
        UserEntity userSavedEntity = userJpaRepository.save(userEntity);
        return mapper.toDomain(userSavedEntity);
    }

    @Override
    public void delete(UUID id) {
        userJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }
}