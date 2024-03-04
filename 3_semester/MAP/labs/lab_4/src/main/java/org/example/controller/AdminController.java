package org.example.controller;

import org.example.model.Admin;
import org.example.repo.IRepository;
import org.example.repo.RepoTypes;
import org.example.utils.factory.AdminRepoFactory;

import java.util.List;

public class AdminController {
    private IRepository<Admin> adminIRepository;
    private static RepoTypes repoType;
    private static AdminController instance;
    private int adminIdCounter;
    private AdminController() {
        if(repoType == null) {
            throw new RuntimeException("repo type not provided");
        }
        this.adminIRepository = AdminRepoFactory.createIRepository(repoType);
        this.adminIdCounter = adminIRepository.getObjects().size() + 1;
    }

    public static void setRepoType(RepoTypes rT) {
        repoType = rT;
    }
    public static AdminController getInstance() {
        if(instance == null) {
            instance = new AdminController();
        }
        return instance;
    }

    public Admin findAdmin(String username) {
        List<Admin> database = adminIRepository.getObjects();
        for(Admin admin: database) {
            if(admin.getUsername() == username){
                return admin;
            }
        }
        return null;
    }
    public boolean createAdmin(String username, String password, String adminRole) {
        if(findAdmin(username) != null) {
            return false;
        }
        Admin newAdmin = new Admin(adminIdCounter++, username, password, adminRole);
        adminIRepository.save(newAdmin);
        return true;
    }

    public void updateAdmin(Admin admin) {

    }

    public boolean deleteAdmin(String username) {
        Admin admin = findAdmin(username);
        if(admin == null) {
            return false;
        }
        adminIRepository.delete(admin);
        return true;
    }
}
