package com.api.domain.usecase.user;

import com.api.domain.exception.UserEmailAlreadyExistsException;
import com.api.domain.exception.UserFieldsNullException;
import com.api.domain.exception.UserNotFoundException;
import com.api.domain.model.User;
import com.api.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private GetUserById getUserById;

    @InjectMocks
    private UpdateUser updateUser;

    @Test
    void updateUser_ShouldThrowUserFieldsNullException_WhenAllUserFieldsAreNull() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName(null);
        user.setEmail(null);

        UUID userId = user.getId();

        UserFieldsNullException exception = assertThrows(UserFieldsNullException.class, () -> updateUser.update(userId, user));

        assertEquals("Todos os campos estão nulos. Pelo menos um campo deve ser preenchido.", exception.getMessage());
        verifyNoInteractions(getUserById);
        verifyNoInteractions(userRepository);
    }

    @Test
    void updateUser_ShouldThrowUserNotFoundException_WhenUserDoesNotExist() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName("Test");
        user.setEmail("test@gmail.com");

        UUID userId = user.getId();

        when(getUserById.getById(userId)).thenThrow(new UserNotFoundException("Usuário não encontrado."));

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> updateUser.update(userId, user));

        assertEquals("Usuário não encontrado.", exception.getMessage());
        verify(getUserById).getById(userId);
        verifyNoInteractions(userRepository);
    }

    @Test
    void updateUser_ShouldThrowUserEmailAlreadyExistsException_WhenEmailAlreadyExists() {
        UUID userId = UUID.randomUUID();

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setName("Test");
        existingUser.setEmail("old@gmail.com");

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setName("Test");
        updatedUser.setEmail("new@gmail.com");

        when(getUserById.getById(userId)).thenReturn(existingUser);
        when(userRepository.existsByEmail("new@gmail.com")).thenReturn(true);

        UserEmailAlreadyExistsException exception = assertThrows(UserEmailAlreadyExistsException.class, () -> updateUser.update(userId, updatedUser));

        assertEquals("Esse e-mail já existe.", exception.getMessage());

        verify(getUserById).getById(userId);
        verify(userRepository).existsByEmail("new@gmail.com");
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void updateUser_ShouldUpdateUser_WhenUserExists() {
        UUID userId = UUID.randomUUID();

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setName("Test");
        existingUser.setEmail("old@gmail.com");

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setName("Test");
        updatedUser.setEmail("new@gmail.com");

        when(getUserById.getById(userId)).thenReturn(existingUser);
        when(userRepository.existsByEmail("new@gmail.com")).thenReturn(false);
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        User result = updateUser.update(userId, updatedUser);

        assertEquals("new@gmail.com", result.getEmail());

        verify(getUserById).getById(userId);
        verify(userRepository).existsByEmail("new@gmail.com");
        verify(userRepository).save(existingUser);
    }
}