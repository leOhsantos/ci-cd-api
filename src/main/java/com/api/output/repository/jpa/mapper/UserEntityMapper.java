package com.api.output.repository.jpa.mapper;

import com.api.domain.model.User;
import com.api.output.repository.jpa.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapper {
    public User toDomain(UserEntity dto) {
        if (dto == null) return null;

        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }

    public UserEntity toEntity(User dto) {
        return new UserEntity(
                dto.getName(),
                dto.getEmail(),
                dto.getPassword()
        );
    }
}