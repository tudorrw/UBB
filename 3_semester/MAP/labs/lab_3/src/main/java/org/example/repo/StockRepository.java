package org.example.repo;

import org.example.model.Company;
import org.example.model.Stock;
import org.example.utils.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class StockRepository implements IRepository<Stock> {
    private List<Stock> stocks;
    private static StockRepository instance;


    private StockRepository() {
        this.stocks = new ArrayList<>();
    }

    @Override
    public List<Stock> getObjects() {
        return stocks;
    }

    @Override
    public void save(Stock entity) {
        stocks.add(entity);
    }

    @Override
    public void update(Stock entity, String action) {

    }

    @Override
    public void delete(Stock object) {
        stocks.remove(object);
    }

    public static StockRepository getInstance() {
        if (instance == null) {
            instance = new StockRepository();
        }
        return instance;
    }


}
