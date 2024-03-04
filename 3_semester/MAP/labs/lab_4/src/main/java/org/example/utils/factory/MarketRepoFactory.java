package org.example.utils.factory;

import org.example.model.Market;
import org.example.repo.IRepository;
import org.example.repo.RepoTypes;
import org.example.repo.databaseRepo.MarketRepositoryDB;
import org.example.repo.inMemoryRepo.MarketRepositoryIM;

public class MarketRepoFactory {
    public static IRepository<Market> createIRepository(RepoTypes rT) {
        return switch (rT) {
            case database -> MarketRepositoryDB.getInstance();
            case inMemory -> MarketRepositoryIM.getInstance();
        };
    }
}
