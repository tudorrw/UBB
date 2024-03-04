package org.example.controller;

import org.example.repo.IRepository;
import org.example.repo.RepoTypes;
import org.example.model.User;
import org.example.utils.factory.UserRepoFactory;

import java.util.List;

public class UserController {
    private static IRepository<User> userIRepository;
    private static RepoTypes repoType;
    private static UserController instance;
    private int userIdCounter;

    private UserController() {
        if(repoType == null) {
            throw new RuntimeException("repo type not provided!");
        }
        userIRepository = UserRepoFactory.createIRepository(repoType);
        this.userIdCounter = userIRepository.getObjects().size() + 1;
    }
    public static UserController getInstance() {
        if(instance == null) {
            instance = new UserController();
        }
        return instance;
    }


    public User findUser(String username, String password) {
        List<User> database = userIRepository.getObjects();
        for(User user: database) {
            if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
    public User findUser(String username) {
        List<User> database = userIRepository.getObjects();
        for(User user: database) {
            if(user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public boolean createUser(String username, String password) {
        if(findUser(username) != null) {
            return false;
        }
        User newUser = new User(userIdCounter++, username, password);
        userIRepository.save(newUser);
        return true;
    }

    public void updateUser() {

    }

    public boolean deleteUser(String username, String password) {
        User user = findUser(username, password);
        if(user == null) {
            return false;
        }
        userIRepository.delete(user);
        return true;
    }
    public boolean deleteUser(String username) {
        User user = findUser(username);
        if(user == null) {
            return false;
        }
        userIRepository.delete(user);
        return true;
    }
    public List<User> showAllUsers() {
         return userIRepository.getObjects();

    }
    public static void setRepoType(RepoTypes rT) {
        repoType = rT;
    }

}
