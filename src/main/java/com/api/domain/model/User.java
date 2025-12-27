package com.api.domain.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private UUID id;
    private String name;
    private String email;
    private String password;
}