package org.example.utils.factory;

import org.example.model.Admin;
import org.example.repo.IRepository;
import org.example.repo.RepoTypes;
import org.example.repo.databaseRepo.AdminRepositoryDB;
import org.example.repo.inMemoryRepo.AdminRepositoryIM;

public class AdminRepoFactory {
    public static IRepository<Admin> createIRepository(RepoTypes rT) {
        return switch (rT) {
            case database -> AdminRepositoryDB.getInstance();
            case inMemory -> AdminRepositoryIM.getInstance();
        };
    }
}
