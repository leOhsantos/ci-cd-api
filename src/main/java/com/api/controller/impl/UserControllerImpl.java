package com.api.controller.impl;

import com.api.controller.UserController;
import com.api.dto.user.UserMapper;
import com.api.dto.user.UserRequestDto;
import com.api.dto.user.UserResponseDto;
import com.api.dto.user.UserUpdateRequestDto;
import com.api.entity.User;
import com.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @Override
    public ResponseEntity<List<UserResponseDto>> getUsers() {
        List<User> users = userService.getUsers();

        if (users.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        List<UserResponseDto> userResponseDto = users.stream()
                .map(UserMapper::toUserResponseDto)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }

    @Override
    public ResponseEntity<UserResponseDto> getUserById(UUID id) {
        User user = userService.getUserById(id);
        UserResponseDto userResponseDto = UserMapper.toUserResponseDto(user);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }

    @Override
    public ResponseEntity<UserResponseDto> saveUser(UserRequestDto dto) {
        User user = userService.saveUser(UserMapper.toUser(dto));
        UserResponseDto userResponseDto = UserMapper.toUserResponseDto(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }

    @Override
    public ResponseEntity<UserResponseDto> updateUser(UUID id, UserUpdateRequestDto dto) {
        User user = userService.updateUser(id, UserMapper.toUser(dto));
        UserResponseDto userResponseDto = UserMapper.toUserResponseDto(user);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }

    @Override
    public ResponseEntity<Void> deleteUser(UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}