package org.example.utils.factory;

import org.example.model.ValueStock;
import org.example.repo.IRepository;
import org.example.repo.RepoTypes;
import org.example.repo.databaseRepo.ValueStockRepositoryDB;
import org.example.repo.inMemoryRepo.ValueStockRepositoryIM;

public class ValueStockRepoFactory {
    public static IRepository<ValueStock> createIRepository(RepoTypes rT) {
        return switch (rT) {
            case database -> ValueStockRepositoryDB.getInstance();
            case inMemory -> ValueStockRepositoryIM.getInstance();
        };
    }
}
