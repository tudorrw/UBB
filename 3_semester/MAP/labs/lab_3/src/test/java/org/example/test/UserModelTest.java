package org.example.test;

import org.example.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserModelTest {
    private User user;

    @Test
    void getId() {
        int userId = 1;
        user = new User(5, "qwerty", "1234");
        assertEquals(userId, user.getId());
    }

    @Test
    void setId() {
        int userId = 1;
        user = new User(6, "qwerty", "1234");
        user.setId(userId);
        assertEquals(userId, user.getId());
    }

    @Test
    void getUsername() {
        String username = "qwerty";
        user = new User(1, username, "1234");
        assertEquals(username, user.getUsername());
    }

    @Test
    void setUsername() {
        String username = "newusername";
        user = new User(1, "qwerty", "1234");
        user.setUsername(username);
        assertEquals(username, user.getUsername());
    }

    @Test
    void getPassword() {
        String password = "1234";
        user = new User(1, "qwerty", "1234");
        assertEquals(password, user.getPassword());
    }

    @Test
    void setPassword() {
        String password = "5678";
        user = new User(1, "qwerty", "1234");
        user.setPassword(password);
        assertEquals(password, user.getPassword());
    }
}
