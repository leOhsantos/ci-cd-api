package com.api.domain.usecase.user;

import com.api.domain.exception.UserEmailAlreadyExistsException;
import com.api.domain.exception.UserFieldsNullException;
import com.api.domain.model.User;
import com.api.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUser {
    private final UserRepository userRepository;
    private final GetUserById getUserById;

    @Transactional
    public User update(UUID id, User updatedUser) {
        if (updatedUser.getName() == null && updatedUser.getEmail() == null)
            throw new UserFieldsNullException("Todos os campos estão nulos. Pelo menos um campo deve ser preenchido.");

        User user = getUserById.getById(id);

        if (
                updatedUser.getEmail() != null &&
                        !updatedUser.getEmail().equals(user.getEmail()) &&
                        userRepository.existsByEmail(updatedUser.getEmail())
        ) {
            throw new UserEmailAlreadyExistsException("Esse e-mail já existe.");
        }

        if (updatedUser.getName() != null) user.setName(updatedUser.getName());
        if (updatedUser.getEmail() != null) user.setEmail(updatedUser.getEmail());

        return userRepository.save(user);
    }
}