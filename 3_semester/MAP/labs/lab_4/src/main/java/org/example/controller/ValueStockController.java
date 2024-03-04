package org.example.controller;

import org.example.model.Company;
import org.example.model.Market;
import org.example.model.ValueStock;
import org.example.repo.IRepository;
import org.example.repo.RepoTypes;
import org.example.utils.factory.ValueStockRepoFactory;

import java.util.List;

public class ValueStockController {
    private IRepository<ValueStock> valueStockIRepository;
    private static RepoTypes repoType;
    private static ValueStockController instance;

    private ValueStockController() {
        if(repoType == null) {
            throw new RuntimeException("repo type not provided");
        }
        valueStockIRepository = ValueStockRepoFactory.createIRepository(repoType);
    }
    public static ValueStockController getInstance() {
        if(instance == null) {
            instance = new ValueStockController();
        }
        return instance;
    }
    public static void setRepoType(RepoTypes rT) {
        repoType = rT;
    }

    public boolean addValueStock(int id, String name, Company company, Market market, double dividend){
        if(!this.searchValueStockBool(id)) {
            valueStockIRepository.save(new ValueStock(id, name,company,market,dividend));
            return true;
        }
        else {
            return false;
        }
    }

    public boolean removeValueStock(int id){
        ValueStock search = this.searchValueStock(id);
        if(search != null) {
            valueStockIRepository.delete(search);
            return true;
        }
        else{
            return false;
        }
    }

    public List<ValueStock> getAll(){
        return valueStockIRepository.getObjects();
    }

    public boolean searchValueStockBool(int id){
        List<ValueStock> valueStocks = valueStockIRepository.getObjects();
        for (ValueStock valueStock: valueStocks) {
            if(valueStock.getId() == id){
                return true;
            }
        }
        return false;
    }

    public ValueStock searchValueStock(int id){
        List<ValueStock> valueStocks = valueStockIRepository.getObjects();
        for (ValueStock valueStock: valueStocks) {
            if(valueStock.getId() == id){
                return valueStock;
            }
        }
        return null;
    }
}
