package com.api.domain.usecase.user;

import com.api.domain.exception.UserNotFoundException;
import com.api.domain.model.User;
import com.api.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUserByIdTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUserById getUserById;

    @Test
    void getUserById_ShouldThrowUserNotFoundException_WhenUserDoesNotExist() {
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserNotFoundException.class, () -> getUserById.getById(userId));

        assertEquals("Usuário não encontrado.", exception.getMessage());
        verify(userRepository).findById(userId);
    }

    @Test
    void getUserById_ShouldReturnUser_WhenUserExists() {
        User user = new User(UUID.randomUUID(), "Test", "test@gmail.com", "12345678");

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        User result = getUserById.getById(user.getId());

        assertEquals(user, result);
        verify(userRepository).findById(user.getId());
    }
}