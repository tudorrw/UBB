package map.project.stockApp.service;


import map.project.stockApp.model.Market;
import map.project.stockApp.repo.RepoTypes;
import map.project.stockApp.repo.springRepo.MarketRepositorySpring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarketService {
    @Autowired
    private MarketRepositorySpring repository;
    private static RepoTypes repoType;
    private static MarketService instance;
    private int marketIdCounter;

    private MarketService() {
    }

    public static void setRepoType(RepoTypes rT) {
        repoType = rT;
    }

    public static MarketService getInstance() {
        if (instance == null) {
            instance = new MarketService();
        }
        return instance;
    }


    public List<Market> getAll() {
        return repository.findAll();
    }


    public Market searchMarket(String name) {
        return repository.findByName(name);
    }



    public void addMarketObject(Market market) {
        if (!repository.existsById(market.getId()) && !repository.existsMarketByName(market.getName())) {
            repository.save(market);
        }
    }

    public void deleteMarket(int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
    }

    public void updateMarket(int id, String newName, String newLocation) {
        Market existingMarket = repository.findById(id).orElse(null);
        if (existingMarket != null) {
            existingMarket.setName(newName);
            existingMarket.setLocation(newLocation);
            repository.save(existingMarket);
        }
    }
}
