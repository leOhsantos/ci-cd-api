package com.api.input.controller;

import com.api.domain.model.User;
import com.api.domain.usecase.user.*;
import com.api.input.mapper.UserMapper;
import com.api.input.dto.user.UserRequestDto;
import com.api.input.dto.user.UserResponseDto;
import com.api.input.dto.user.UserUpdateRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class UserController implements UserApi {
    private final GetUsers getUsers;
    private final GetUserById getUserById;
    private final SaveUser saveUser;
    private final UpdateUser updateUser;
    private final DeleteUser deleteUser;

    @Override
    public ResponseEntity<List<UserResponseDto>> getUsers() {
        List<User> users = getUsers.get();

        if (users.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        List<UserResponseDto> userResponseDto = users.stream()
                .map(UserMapper::toUserResponseDto)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }

    @Override
    public ResponseEntity<UserResponseDto> getUserById(UUID id) {
        User user = getUserById.getById(id);
        UserResponseDto userResponseDto = UserMapper.toUserResponseDto(user);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }

    @Override
    public ResponseEntity<UserResponseDto> saveUser(UserRequestDto dto) {
        User user = saveUser.save(UserMapper.toUser(dto));
        UserResponseDto userResponseDto = UserMapper.toUserResponseDto(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }

    @Override
    public ResponseEntity<UserResponseDto> updateUser(UUID id, UserUpdateRequestDto dto) {
        User user = updateUser.update(id, UserMapper.toUser(dto));
        UserResponseDto userResponseDto = UserMapper.toUserResponseDto(user);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }

    @Override
    public ResponseEntity<Void> deleteUser(UUID id) {
        deleteUser.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}