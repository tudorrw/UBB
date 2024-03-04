package map.project.stockApp.service;

import lombok.AllArgsConstructor;
import map.project.stockApp.model.Admin;
import map.project.stockApp.repo.springRepo.AdminRepositorySpring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {
    @Autowired
    private AdminRepositorySpring repository;

    private AdminService() {
    }


    public Admin createAdmin(Admin admin) {
        if (!repository.existsById(admin.getId()) && !repository.existsAdminByUsername(admin.getUsername())) {
            repository.save(admin);
            return admin;
        }
        return null;
    }

    public void deleteAdmin(int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
    }

    public void updateAdmin(String oldUsername, String newUsername, String newPassword) {
        Admin admin = repository.findByUsername(oldUsername);
        if (admin != null) {
            admin.setUsername(newUsername);
            admin.setPassword(newPassword);
            repository.save(admin);
        }
    }

    public List<Admin> showAllAdmins() {
        return repository.findAll();
    }


    public Admin findAdmin(String username, String password) {
        return this.repository.findByUsernameAndPassword(username, password);
    }
}
