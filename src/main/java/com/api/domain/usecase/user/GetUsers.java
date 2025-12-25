package com.api.domain.usecase.user;

import com.api.domain.model.User;
import com.api.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class GetUsers {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> get() {
        return userRepository.findAll();
    }
}