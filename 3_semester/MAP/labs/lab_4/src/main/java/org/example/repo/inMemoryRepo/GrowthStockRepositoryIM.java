package org.example.repo.inMemoryRepo;

import org.example.model.GrowthStock;
import org.example.repo.IRepository;

import java.util.ArrayList;
import java.util.List;

public class GrowthStockRepositoryIM implements IRepository<GrowthStock> {
    private List<GrowthStock> growthStocks;
    private static GrowthStockRepositoryIM instance;

    private GrowthStockRepositoryIM() {
        this.growthStocks = new ArrayList<>();
    }

    @Override
    public List<GrowthStock> getObjects() {
        return growthStocks;
    }

    @Override
    public void save(GrowthStock entity) {
        growthStocks.add(entity);
    }

    @Override
    public void update(GrowthStock entity) {

    }

    @Override
    public void delete(GrowthStock object) {
        growthStocks.remove(object);
    }

    public static GrowthStockRepositoryIM getInstance() {
        if (instance == null) {
            instance = new GrowthStockRepositoryIM();
        }
        return instance;
    }


}
