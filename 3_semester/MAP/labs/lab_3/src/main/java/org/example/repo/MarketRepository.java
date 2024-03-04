package org.example.repo;

import org.example.model.Market;
import org.example.model.ValueStock;
import org.example.utils.observer.Observable;
import org.example.utils.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class MarketRepository implements IRepository<Market>{
    private List<Market> markets;
    private static MarketRepository instance;


    void insert_values() {
        Market usMarket = new Market(1, "US Stock Market", "New York");
        Market euMarket = new Market(2, "EU Stock Market", "London");
        markets.add(usMarket);
        markets.add(euMarket);
    }
    private MarketRepository() {

        this.markets = new ArrayList<>();
        insert_values();
    }

    @Override
    public List<Market> getObjects() {
        return markets;
    }

    @Override
    public void save(Market entity) {
        markets.add(entity);
    }

    @Override
    public void update(Market entity, String action) {

    }

    @Override
    public void delete(Market object) {
        markets.remove(object);
    }


    public static MarketRepository getInstance() {
        if (instance == null) {
            instance = new MarketRepository();
        }
        return instance;
    }



}
