package org.example.controller;

import org.example.model.Admin;
import org.example.repo.AdminRepository;
import java.util.List;

public class AdminController {
    private AdminRepository adminRepository;
    private int adminIdCounter;
    public AdminController(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
        this.adminIdCounter = adminRepository.getObjects().size() + 1;
    }

    public Admin findAdmin(String username) {
        List<Admin> database = adminRepository.getObjects();
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
        adminRepository.save(newAdmin);
        return true;
    }

    public void updateAdmin(Admin admin) {

    }

    public boolean deleteAdmin(String username) {
        Admin admin = findAdmin(username);
        if(admin == null) {
            return false;
        }
        adminRepository.delete(admin);
        return true;
    }
}
