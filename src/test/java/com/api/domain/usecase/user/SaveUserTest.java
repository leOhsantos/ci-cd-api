package com.api.domain.usecase.user;

import com.api.domain.exception.UserEmailAlreadyExistsException;
import com.api.domain.model.User;
import com.api.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaveUserTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SaveUser saveUser;

    @Test
    void saveUser_ShouldThrowUserEmailAlreadyExistsException_WhenEmailAlreadyExists() {
        User user = new User(UUID.randomUUID(), "Test", "test@gmail.com", "12345678");

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        Exception exception = assertThrows(UserEmailAlreadyExistsException.class, () -> saveUser.save(user));

        assertEquals("Esse e-mail já existe.", exception.getMessage());
        verify(userRepository).existsByEmail(user.getEmail());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void saveUser_ShouldSaveUser_WhenEmailDoesNotExist() {
        User user = new User(UUID.randomUUID(), "Test", "test@gmail.com", "12345678");

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        User result = saveUser.save(user);

        assertEquals(user, result);

        verify(userRepository).existsByEmail(user.getEmail());
        verify(userRepository).save(user);
    }
}