package com.api.domain.usecase.user;

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
class DeleteUserTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private GetUserById getUserById;

    @InjectMocks
    private DeleteUser deleteUser;

    @Test
    void deleteUser_ShouldThrowUserNotFoundException_WhenUserDoesNotExist() {
        UUID userId = UUID.randomUUID();

        when(getUserById.getById(userId)).thenThrow(new UserNotFoundException("Usuário não encontrado."));

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> deleteUser.delete(userId));

        assertEquals("Usuário não encontrado.", exception.getMessage());
        verify(getUserById).getById(userId);
        verifyNoInteractions(userRepository);
    }

    @Test
    void deleteUser_ShouldDeleteUser_WhenUserExists() {
        User user = new User(UUID.randomUUID(), "Test", "test@gmail.com", "12345678");
        UUID userId = user.getId();

        when(getUserById.getById(userId)).thenReturn(user);
        doNothing().when(userRepository).delete(userId);

        deleteUser.delete(userId);

        verify(getUserById).getById(userId);
        verify(userRepository).delete(userId);
    }
}