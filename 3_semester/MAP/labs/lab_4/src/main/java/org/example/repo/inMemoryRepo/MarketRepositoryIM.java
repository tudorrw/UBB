package org.example.repo.inMemoryRepo;

import org.example.model.Market;
import org.example.repo.IRepository;

import java.util.ArrayList;

public class MarketRepositoryIM implements IRepository<Market> {
    private ArrayList<Market> markets;
    private static MarketRepositoryIM instance;


    void insert_values() {
        Market usMarket = new Market(1, "US Stock Market", "New York");
        Market euMarket = new Market(2, "EU Stock Market", "London");
        markets.add(usMarket);
        markets.add(euMarket);
    }
    private MarketRepositoryIM() {

        this.markets = new ArrayList<>();
        insert_values();
    }

    @Override
    public ArrayList<Market> getObjects() {
        return markets;
    }

    @Override
    public void save(Market entity) {
        markets.add(entity);
    }

    @Override
    public void update(Market entity) {

    }

    @Override
    public void delete(Market object) {
        markets.remove(object);
    }


    public static MarketRepositoryIM getInstance() {
        if (instance == null) {
            instance = new MarketRepositoryIM();
        }
        return instance;
    }



}
