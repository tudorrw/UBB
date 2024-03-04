package org.example.controller;


import org.example.model.Market;
import org.example.repo.IRepository;

import java.util.List;

public class MarketController {
    private IRepository<Market> repository;

    public MarketController(IRepository<Market> repository) {
        this.repository = repository;
    }

    public boolean addMarket(int id, String name, String location){
        if(!this.searchMarketBool(id)) {
            repository.save(new Market(id, name, location));
            return true;
        }
        else{
            return false;
        }
    }

    public boolean removeMarket(int id){
        Market search = this.searchMarket(id);
        if(search != null) {
            repository.delete(search);
            return true;
        }
        else{
            return false;
        }
    }

    public List<Market> getAll(){
        return repository.getObjects();
    }

    public boolean searchMarketBool(int id){
        List<Market> markets = repository.getObjects();
        for (Market market: markets) {
            if(market.getId() == id){
                return true;
            }
        }
        return false;
    }

    public Market searchMarket(int id){
        List<Market> markets = repository.getObjects();
        for (Market market: markets) {
            if(market.getId() == id){
                return market;
            }
        }
        return null;
    }
}
