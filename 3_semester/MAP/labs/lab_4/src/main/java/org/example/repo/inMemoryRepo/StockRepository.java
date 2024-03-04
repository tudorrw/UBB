package org.example.repo.inMemoryRepo;

import org.example.model.Stock;
import org.example.repo.IRepository;

import java.util.ArrayList;

public class StockRepository implements IRepository<Stock> {
    private ArrayList<Stock> stocks;
    private static StockRepository instance;


    private StockRepository() {
        this.stocks = new ArrayList<>();
    }

    @Override
    public ArrayList<Stock> getObjects() {
        return stocks;
    }

    @Override
    public void save(Stock entity) {
        stocks.add(entity);
    }

    @Override
    public void update(Stock entity) {

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
