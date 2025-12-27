package com.api.input.mapper;

import com.api.domain.model.User;
import com.api.input.dto.user.UserRequestDto;
import com.api.input.dto.user.UserResponseDto;
import com.api.input.dto.user.UserUpdateRequestDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toDomain(UserRequestDto dto) {
        if (dto == null) return null;

        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }

    public User toDomain(UserUpdateRequestDto dto) {
        if (dto == null) return null;

        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
    }

    public UserResponseDto toResponse(User user) {
        if (user == null) return null;

        return UserResponseDto.builder()
                .id(user.getId())
                .nome(user.getName())
                .email(user.getEmail())
                .build();
    }
}