package com.api.dto.user;

import com.api.entity.User;

public class UserMapper {
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