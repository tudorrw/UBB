package org.example.repo.inMemoryRepo;

import org.example.model.ValueStock;
import org.example.repo.IRepository;

import java.util.ArrayList;
import java.util.List;

public class ValueStockRepositoryIM implements IRepository<ValueStock> {
    private List<ValueStock> valueStocks;
    private static ValueStockRepositoryIM instance;

    private ValueStockRepositoryIM() {
        this.valueStocks = new ArrayList<>();
    }

    @Override
    public List<ValueStock> getObjects() {
        return valueStocks;
    }

    @Override
    public void save(ValueStock entity) {
        valueStocks.add(entity);
    }

    @Override
    public void update(ValueStock entity) {

    }

    @Override
    public void delete(ValueStock object) {
        valueStocks.remove(object);
    }


    public static ValueStockRepositoryIM getInstance() {
        if (instance == null) {
            instance = new ValueStockRepositoryIM();
        }
        return instance;
    }


}