package map.project.stockApp.service;

import map.project.stockApp.model.User;
import map.project.stockApp.repo.springRepo.UserRepositorySpring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


    private final UserRepositorySpring repository;

    @Autowired
    public UserService(UserRepositorySpring repository) {
        this.repository = repository;
    }


    public User findUser(String username, String password) {
        return this.repository.findByUsernameAndPassword(username, password);
    }

    public User findUser(String username) {
        return this.repository.findUserByUsername(username);
    }

    //for spring
    public User createUser(User user) {
        if (!repository.existsById(user.getId()) && !repository.existsUserByUsername(user.getUsername())) {
            repository.save(user);
            return user;
        }
        return null;
    }

    public void deleteUser(int id) {
        if (!repository.existsById(id)) {
            repository.deleteById(id);
        }
    }

    public void updateUser(int id, String newUsername, String newPassword) {
        User user = repository.findById(id).orElse(null);
        if (user != null) {
            user.setUsername(newUsername);
            user.setPassword(newPassword);
            repository.save(user);
        }
    }

    public List<User> showAllUsers() {
        return (List<User>) repository.findAll();
    }
}
