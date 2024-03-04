package org.example.controller;

import org.example.model.Company;
import org.example.model.GrowthStock;
import org.example.model.Market;
import org.example.repo.IRepository;
import org.example.repo.RepoTypes;
import org.example.utils.factory.GrowthStockRepoFactory;

import java.util.List;

public class GrowthStockController {
    private IRepository<GrowthStock> growthStockIRepository;
    private static RepoTypes repoType;
    private static GrowthStockController instance;

    private GrowthStockController() {
        if(repoType == null) {
            throw new RuntimeException("repo type not provided");
        }
        growthStockIRepository = GrowthStockRepoFactory.createIRepository(repoType);
    }

    public static GrowthStockController getInstance() {
        if(instance == null) {
            instance = new GrowthStockController();
        }
        return instance;
    }

    public static void setRepoType(RepoTypes rT) {
        repoType = rT;
    }
    public boolean addGrowthStock(int id, String name, Company company, Market market, int growth_rate){
        if(!this.searchGrowthStockBool(id)) {
            growthStockIRepository.save(new GrowthStock(id, name, company,market,growth_rate));
            return true;
        }
        else{
            return false;
        }
    }

    public boolean removeGrowthStock(int id){
        GrowthStock search = this.searchGrowthStock(id);
        if(search != null) {
            growthStockIRepository.delete(search);
            return true;
        }
        else{
            return false;
        }
    }

    public List<GrowthStock> getAll(){
        return growthStockIRepository.getObjects();
    }

    public boolean searchGrowthStockBool(int id){
        List<GrowthStock> growthStocks = growthStockIRepository.getObjects();
        for (GrowthStock growthStock: growthStocks) {
            if(growthStock.getId() == id){
                return true;
            }
        }
        return false;
    }

    public GrowthStock searchGrowthStock(int id){
        List<GrowthStock> growthStocks = growthStockIRepository.getObjects();
        for (GrowthStock growthStock: growthStocks) {
            if(growthStock.getId() == id){
                return growthStock;
            }
        }
        return null;
    }
}
