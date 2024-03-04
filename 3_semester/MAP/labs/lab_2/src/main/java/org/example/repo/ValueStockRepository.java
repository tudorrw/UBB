package org.example.repo;

import org.example.model.ValueStock;

import java.util.ArrayList;
import java.util.List;

public class ValueStockRepository implements IRepository<ValueStock> {
    private List<ValueStock> valueStocks;

    public ValueStockRepository() {
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
    public void delete(ValueStock object) {
        valueStocks.remove(object);
    }
}
