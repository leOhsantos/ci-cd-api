package com.api.service.impl;

import com.api.entity.User;
import com.api.exception.user.UserEmailAlreadyExistsException;
import com.api.exception.user.UserNotFoundException;
import com.api.repository.UserRepository;
import com.api.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void getUsers_ShouldReturnUsers_WhenUsersExist() {
        User user = new User(UUID.randomUUID(), "Test", "test@gmail.com", "12345678");
        List<User> users = new ArrayList<>();
        users.add(user);

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getUsers();

        assertEquals(users, result);
        verify(userRepository).findAll();
    }

    @Test
    void getUsers_ShouldReturnEmptyList_WhenNoUsersExist() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<User> result = userService.getUsers();

        assertTrue(result.isEmpty());
        verify(userRepository).findAll();
    }

    @Test
    void getUserById_ShouldReturnUser_WhenUserExists() {
        UUID id = UUID.randomUUID();
        User user = new User(id, "Test", "test@gmail.com", "12345678");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        User result = userService.getUserById(id);

        assertEquals(user, result);
        verify(userRepository).findById(id);
    }

    @Test
    void getUserById_ShouldThrowUserNotFoundException_WhenUserDoesNotExist() {
        UUID id = UUID.randomUUID();

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(id));
        verify(userRepository).findById(id);
    }

    @Test
    void saveUser_ShouldThrowUserEmailAlreadyExistsException_WhenEmailAlreadyExists() {
        User user = new User(UUID.randomUUID(), "Test", "test@gmail.com", "12345678");

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        assertThrows(UserEmailAlreadyExistsException.class, () -> userService.saveUser(user));
        verify(userRepository).existsByEmail(user.getEmail());
    }

    @Test
    void saveUser_ShouldSaveUser_WhenEmailDoesNotExist() {
        User user = new User(UUID.randomUUID(), "Test", "test@gmail.com", "12345678");

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.saveUser(user);

        assertEquals(user, result);
        verify(userRepository).save(user);
    }

    @Test
    void updateUser_ShouldThrowUserNotFoundException_WhenUserDoesNotExist() {
        User user = new User();
        user.setName("Test");
        user.setEmail("test@gmail.com");

        when(userRepository.findById(user.getId())).thenThrow(new UserNotFoundException("Usuário não encontrado."));

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(user.getId(), user));
        verify(userRepository).findById(user.getId());
    }

    @Test
    void updateUser_ShouldThrowUserEmailAlreadyExistsException_WhenEmailAlreadyExists() {
        User user = new User();
        user.setName("Test");
        user.setEmail("test@gmail.com");

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        assertThrows(UserEmailAlreadyExistsException.class, () -> userService.updateUser(user.getId(), user));
        verify(userRepository).existsByEmail(user.getEmail());
    }

    @Test
    void updateUser_ShouldUpdateUser_WhenUserExists() {
        User user = new User(UUID.randomUUID(), "Test", "test@gmail.com", "12345678");

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.updateUser(user.getId(), user);

        assertEquals(user, result);

        verify(userRepository).existsByEmail(user.getEmail());
        verify(userRepository).findById(user.getId());
        verify(userRepository).save(user);
    }

    @Test
    void deleteUser_ShouldThrowUserNotFoundException_WhenUserDoesNotExist() {
        UUID id = UUID.randomUUID();

        when(userRepository.findById(id)).thenThrow(new UserNotFoundException("Usuário não encontrado."));

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(id));
        verify(userRepository).findById(id);
    }

    @Test
    void deleteUser_ShouldDeleteUser_WhenUserExists() {
        User user = new User(UUID.randomUUID(), "Test", "test@gmail.com", "12345678");

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);

        userService.deleteUser(user.getId());

        verify(userRepository).findById(user.getId());
        verify(userRepository).delete(user);
    }

    @Test
    void existsUserByEmail_ShouldReturnFalse_WhenUserDoesNotExist() {
        String email = "test@gmail.com";

        when(userRepository.existsByEmail(email)).thenReturn(false);

        assertFalse(userService.existsUserByEmail(email));
        verify(userRepository).existsByEmail(email);
    }

    @Test
    void existsUserByEmail_ShouldReturnTrue_WhenUserExists() {
        String email = "test@gmail.com";

        when(userRepository.existsByEmail(email)).thenReturn(true);

        assertTrue(userService.existsUserByEmail(email));
        verify(userRepository).existsByEmail(email);
    }
}