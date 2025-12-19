package com.vvss.FlavorFiesta.example_for_homework;

import com.vvss.FlavorFiesta.controllers.UserController;
import com.vvss.FlavorFiesta.models.User;
import com.vvss.FlavorFiesta.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ModuleAUnitTests {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = new User();
        user1.setId(1L);
        user1.setUsername("testUser1");
        user1.setEmail("test1@example.com");
        user1.setPassword("password123");

        user2 = new User();
        user2.setId(2L);
        user2.setUsername("testUser2");
        user2.setEmail("test2@example.com");
        user2.setPassword("password456");
    }

    @Test
    void testGetAllUsers() {
        // Mock service behavior
        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        // Call controller method
        List<User> response = userController.getAllUsers();

        // Verify response
        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("testUser1", response.get(0).getUsername());
        assertEquals("testUser2", response.get(1).getUsername());

    }

    @Test
    void testGetUserById() {
        // Mock service behavior
        when(userService.getUserById(1L)).thenReturn(user1);

        // Call controller method
        User response = userController.getUserById(1L);

        // Verify response
        assertNotNull(response);
        assertEquals("testUser1", response.getUsername());
        assertEquals("test1@example.com", response.getEmail());
    }

    @Test
    void testCreateUser() {
        // Mock service behavior
        when(userService.saveUser(any(User.class))).thenReturn(user1);

        // Call controller method
        User response = userController.addUser(user1);

        // Verify response
        assertNotNull(response);
        assertEquals("testUser1", response.getUsername());
        assertEquals("test1@example.com", response.getEmail());
    }
} 