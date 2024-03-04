package org.example.utils.factory;


import org.example.model.Company;
import org.example.repo.IRepository;
import org.example.repo.RepoTypes;
import org.example.repo.databaseRepo.CompanyRepositoryDB;
import org.example.repo.inMemoryRepo.CompanyRepositoryIM;

public class CompanyRepoFactory {
    public static IRepository<Company> createIRepository(RepoTypes rT) {
        return switch (rT) {
            case database -> CompanyRepositoryDB.getInstance();
            case inMemory -> CompanyRepositoryIM.getInstance();
        };
    }
}
