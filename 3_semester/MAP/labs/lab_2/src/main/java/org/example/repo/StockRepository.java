package org.example.repo;

import org.example.model.Stock;

import java.util.ArrayList;
import java.util.List;

public class StockRepository implements IRepository<Stock> {
    private List<Stock> stocks;

    public StockRepository() {
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
    public void delete(Stock object) {
        stocks.remove(object);
    }
}
