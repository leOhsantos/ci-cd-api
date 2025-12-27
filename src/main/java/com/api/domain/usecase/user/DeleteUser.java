package com.api.domain.usecase.user;

import com.api.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteUser {
    private final UserRepository userRepository;
    private final GetUserById getUserById;

    @Transactional
    public void delete(UUID id) {
        getUserById.getById(id);
        userRepository.delete(id);
    }
}