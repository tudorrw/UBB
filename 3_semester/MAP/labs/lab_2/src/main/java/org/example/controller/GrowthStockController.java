package org.example.controller;

import org.example.model.GrowthStock;
import org.example.repo.IRepository;

import java.util.List;

public class GrowthStockController {
    private IRepository<GrowthStock> repository;

    public GrowthStockController(IRepository<GrowthStock> repository) {
        this.repository = repository;
    }

    public boolean addGrowthStock(int id, String name, int price,int companyId, int marketId, int growth_rate){
        if(!this.searchGrowthStockBool(id)) {
            repository.save(new GrowthStock(id, name, price,companyId,marketId,growth_rate));
            return true;
        }
        else{
            return false;
        }
    }

    public boolean removeGrowthStock(int id){
        GrowthStock search = this.searchGrowthStock(id);
        if(search != null) {
            repository.delete(search);
            return true;
        }
        else{
            return false;
        }
    }

    public List<GrowthStock> getAll(){
        return repository.getObjects();
    }

    public boolean searchGrowthStockBool(int id){
        List<GrowthStock> growthStocks = repository.getObjects();
        for (GrowthStock growthStock: growthStocks) {
            if(growthStock.getId() == id){
                return true;
            }
        }
        return false;
    }

    public GrowthStock searchGrowthStock(int id){
        List<GrowthStock> growthStocks = repository.getObjects();
        for (GrowthStock growthStock: growthStocks) {
            if(growthStock.getId() == id){
                return growthStock;
            }
        }
        return null;
    }
}
