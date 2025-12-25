package com.api.input.mapper;

import com.api.domain.model.User;
import com.api.input.dto.user.UserRequestDto;
import com.api.input.dto.user.UserResponseDto;
import com.api.input.dto.user.UserUpdateRequestDto;

public class UserMapper {
    private UserMapper() {
    }

    public static User toUser(UserRequestDto dto) {
        if (dto == null) return null;

        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }

    public static User toUser(UserUpdateRequestDto dto) {
        if (dto == null) return null;

        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
    }

    public static UserResponseDto toUserResponseDto(User user) {
        if (user == null) return null;

        return UserResponseDto.builder()
                .id(user.getId())
                .nome(user.getName())
                .email(user.getEmail())
                .build();
    }
}