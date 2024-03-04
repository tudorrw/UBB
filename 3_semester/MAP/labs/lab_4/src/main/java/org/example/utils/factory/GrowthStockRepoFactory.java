package org.example.utils.factory;

import org.example.model.GrowthStock;
import org.example.repo.inMemoryRepo.GrowthStockRepositoryIM;
import org.example.repo.IRepository;
import org.example.repo.RepoTypes;
import org.example.repo.databaseRepo.GrowthStockRepositoryDB;

public class GrowthStockRepoFactory {
    public static IRepository<GrowthStock> createIRepository(RepoTypes rT) {
        return switch (rT) {
            case database -> GrowthStockRepositoryDB.getInstance();
            case inMemory -> GrowthStockRepositoryIM.getInstance();
        };
    }
}
