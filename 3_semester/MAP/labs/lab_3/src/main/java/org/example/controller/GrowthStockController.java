package org.example.controller;

import org.example.model.Company;
import org.example.model.GrowthStock;
import org.example.model.Market;
import org.example.repo.IRepository;

import java.util.List;

public class GrowthStockController {
    private IRepository<GrowthStock> repository;

    public GrowthStockController(IRepository<GrowthStock> repository) {
        this.repository = repository;
    }

    public boolean addGrowthStock(int id, String name, Company company, Market market, int growth_rate){
        if(!this.searchGrowthStockBool(id)) {
            repository.save(new GrowthStock(id, name, company,market,growth_rate));
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
