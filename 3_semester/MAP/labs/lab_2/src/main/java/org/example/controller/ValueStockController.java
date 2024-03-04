package org.example.controller;

import org.example.model.ValueStock;
import org.example.repo.IRepository;

import java.util.List;

public class ValueStockController {
    private IRepository<ValueStock> repository;

    public ValueStockController(IRepository<ValueStock> repository) {
        this.repository = repository;
    }

    public boolean addValueStock(int id, String name, int price,int companyId, int marketId, int divident){
        if(!this.searchValueStockBool(id)) {
            repository.save(new ValueStock(id, name, price,companyId,marketId,divident));
            return true;
        }
        else{
            return false;
        }
    }

    public boolean removeValueStock(int id){
        ValueStock search = this.searchValueStock(id);
        if(search != null) {
            repository.delete(search);
            return true;
        }
        else{
            return false;
        }
    }

    public List<ValueStock> getAll(){
        return repository.getObjects();
    }

    public boolean searchValueStockBool(int id){
        List<ValueStock> valueStocks = repository.getObjects();
        for (ValueStock valueStock: valueStocks) {
            if(valueStock.getId() == id){
                return true;
            }
        }
        return false;
    }

    public ValueStock searchValueStock(int id){
        List<ValueStock> valueStocks = repository.getObjects();
        for (ValueStock valueStock: valueStocks) {
            if(valueStock.getId() == id){
                return valueStock;
            }
        }
        return null;
    }
}
