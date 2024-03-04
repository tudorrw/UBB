package org.example;

import org.example.controller.AdminController;
import org.example.controller.UserController;
import org.example.repo.AdminRepository;
import org.example.repo.UserRepository;
import org.example.view.View;


public class Main {
    public static void main(String[] args) {
        UserRepository userRepo = new UserRepository();
        AdminRepository adminRepo = new AdminRepository();
        UserController userController = new UserController(userRepo);
        AdminController adminController = new AdminController(adminRepo);
        View view = new View(userController, adminController);
        System.out.println("sa vad ca merge faza cu commit urile");
        view.run();

    }
}