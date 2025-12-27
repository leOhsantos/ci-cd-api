package com.api.domain.usecase.user;

import com.api.domain.model.User;
import com.api.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUsersTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUsers getUsers;

    @Test
    void getUsers_ShouldReturnEmptyList_WhenNoUsersExist() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<User> result = getUsers.get();

        assertTrue(result.isEmpty());
        verify(userRepository).findAll();
    }

    @Test
    void getUsers_ShouldReturnUsers_WhenUsersExist() {
        User user = new User(UUID.randomUUID(), "Test", "test@gmail.com", "12345678");
        List<User> users = List.of(user);

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = getUsers.get();

        assertEquals(users, result);
        verify(userRepository).findAll();
    }
}