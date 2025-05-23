package com.api.controller;

import com.api.dto.user.UserRequestDto;
import com.api.dto.user.UserResponseDto;
import com.api.dto.user.UserUpdateRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/users")
public interface UserController {
    @GetMapping
    ResponseEntity<List<UserResponseDto>> getUsers();

    @GetMapping("/{id}")
    ResponseEntity<UserResponseDto> getUserById(@PathVariable UUID id);

    @PostMapping
    ResponseEntity<UserResponseDto> saveUser(@Valid @RequestBody UserRequestDto dto);

    @PatchMapping("/{id}")
    ResponseEntity<UserResponseDto> updateUser(@PathVariable UUID id, @RequestBody UserUpdateRequestDto dto);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteUser(@PathVariable UUID id);
}