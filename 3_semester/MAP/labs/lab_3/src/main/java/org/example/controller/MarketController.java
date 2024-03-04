package org.example.controller;


import org.example.model.Market;
import org.example.repo.IRepository;

import java.util.List;

public class MarketController {
    private IRepository<Market> marketIRepository;
    private int marketIdCounter;

    public MarketController(IRepository<Market> marketIRepository) {
        this.marketIRepository = marketIRepository;
        this.marketIdCounter = marketIRepository.getObjects().size();
    }

    public boolean addMarket(String name, String location){
        if(!this.searchMarketBool(name)) {
            marketIRepository.save(new Market(marketIdCounter++, name, location));
            return true;
        }
        else{
            return false;
        }
    }

    public boolean removeMarket(String name){
        Market search = this.searchMarket(name);
        if(search != null) {
            marketIRepository.delete(search);
            return true;
        }
        else{
            return false;
        }
    }

    public List<Market> getAll(){
        return marketIRepository.getObjects();
    }

    public boolean searchMarketBool(String name){
        List<Market> markets = marketIRepository.getObjects();
        for (Market market: markets) {
            if(market.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public Market searchMarket(String name){
        List<Market> markets = marketIRepository.getObjects();
        for (Market market: markets) {
            if(market.getName().equals(name)){
                return market;
            }
        }
        return null;
    }
}
