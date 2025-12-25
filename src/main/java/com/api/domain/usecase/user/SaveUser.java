package com.api.domain.usecase.user;

import com.api.domain.exception.UserEmailAlreadyExistsException;
import com.api.domain.model.User;
import com.api.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SaveUser {
    private final UserRepository userRepository;

    @Transactional
    public User save(User newUser) {
        if (userRepository.existsByEmail(newUser.getEmail()))
            throw new UserEmailAlreadyExistsException("Esse e-mail já existe.");
        return userRepository.save(newUser);
    }
}