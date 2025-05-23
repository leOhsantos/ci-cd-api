package com.api.service;

import com.api.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> getUsers();
    User getUserById(UUID id);
    User saveUser(User newUser);
    User updateUser(UUID id, User updatedUser);
    void deleteUser(UUID id);
    Boolean existsUserByEmail(String email);
}