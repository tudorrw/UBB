package org.example.repo.inMemoryRepo;

import org.example.model.Admin;
import org.example.repo.IRepository;

import java.util.ArrayList;
import java.util.List;

public class AdminRepositoryIM implements IRepository<Admin> {
    private List<Admin> admins;
    private static AdminRepositoryIM instance;

    private AdminRepositoryIM() {
        this.admins = new ArrayList<>();
    }

    @Override
    public List<Admin> getObjects() {
        return admins;
    }

    @Override
    public void save(Admin entity) {
        admins.add(entity);
    }

    @Override
    public void update(Admin entity) {

    }

    @Override
    public void delete(Admin object) {
        admins.remove(object);
    }

    public static AdminRepositoryIM getInstance() {
        if (instance == null) {
            instance = new AdminRepositoryIM();
        }
        return instance;
    }
}