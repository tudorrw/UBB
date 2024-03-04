package org.example.repo.inMemoryRepo;

import org.example.model.User;
import org.example.repo.IRepository;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryIM implements IRepository<User> {
    private List<User> users;
    private static UserRepositoryIM instance;


    void insert_values() {
        User user1 = new User(1, "tudor", "passwd");
        User user2 = new User(2, "raul", "qwerty");
        User user3 = new User(3, "alex", "123");
        User user4 = new User(4, "ana", "qwerty");
        save(user1);
        save(user2);
        save(user3);
        save(user4);
    }
    private UserRepositoryIM() {
        this.users = new ArrayList<>();
        insert_values();
    }
    public UserRepositoryIM(String message) {
        this.users = new ArrayList<>();
    }

    @Override
    public List<User> getObjects() {
        return users;
    }

    @Override
    public void save(User user) {
        users.add(user);
    }

    @Override
    public void update(User entity) {

    }

//    @Override
//    public void update(User user) {
//
//    }

    @Override
    public void delete(User object) {
        users.remove(object);
    }


    public static UserRepositoryIM getInstance() {
        if (instance == null) {
            instance = new UserRepositoryIM();
        }
        return instance;
    }
}