package com.api.service.impl;

import com.api.exception.user.UserFieldsNullException;
import com.api.exception.user.UserEmailAlreadyExistsException;
import com.api.exception.user.UserNotFoundException;
import com.api.entity.User;
import com.api.repository.UserRepository;
import com.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado."));
    }

    @Override
    public User saveUser(User newUser) {
        if (existsUserByEmail(newUser.getEmail())) throw new UserEmailAlreadyExistsException("Esse e-mail já existe.");
        return userRepository.save(newUser);
    }

    @Override
    public User updateUser(UUID id, User updatedUser) {
        if (updatedUser.getName() == null && updatedUser.getEmail() == null) {
            throw new UserFieldsNullException("Todos os campos estão nulos. Pelo menos um campo deve ser preenchido.");
        }

        User user = getUserById(id);

        if (updatedUser.getName() != null) user.setName(updatedUser.getName());
        if (updatedUser.getEmail() != null) user.setEmail(updatedUser.getEmail());

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.delete(getUserById(id));
    }

    @Override
    public Boolean existsUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}