package org.example.utils.factory;

import org.example.model.User;
import org.example.repo.IRepository;
import org.example.repo.RepoTypes;
import org.example.repo.inMemoryRepo.UserRepositoryIM;
import org.example.repo.databaseRepo.UserRepositoryDB;

public class UserRepoFactory {
    public static IRepository<User> createIRepository(RepoTypes rT) {
        return switch (rT) {
            case database -> UserRepositoryDB.getInstance();
            case inMemory -> UserRepositoryIM.getInstance();
        };
    }
}
