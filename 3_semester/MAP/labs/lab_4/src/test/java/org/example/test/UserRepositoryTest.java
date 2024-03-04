package org.example.test;

import org.example.model.User;
import org.example.repo.inMemoryRepo.UserRepositoryIM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
    private UserRepositoryIM userRepository;

    @BeforeEach
    void setUp(){
        this.userRepository = UserRepositoryIM.getInstance();
    }

    @Test
    void getObjects() {
        assertEquals(4, userRepository.getObjects().size());
    }

    @Test
    void save() {
        User user = new User(5, "qwerty", "1234");
        userRepository.save(user);
        assertEquals(5, userRepository.getObjects().size());
    }

    @Test
    void delete() {
        User user1 = new User(5, "bogdan", "123");
        userRepository.save(user1);
        userRepository.delete(user1);
        assertEquals(4, userRepository.getObjects().size());
    }
}