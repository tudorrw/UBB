package org.example.controller;


import org.example.model.Market;
import org.example.repo.IRepository;
import org.example.repo.RepoTypes;
import org.example.utils.factory.MarketRepoFactory;

import java.util.List;

public class MarketController {
    private IRepository<Market> marketIRepository;
    private static RepoTypes repoType;
    private static MarketController instance;
    private int marketIdCounter;

    private MarketController() {
        if(repoType == null) {
            throw new RuntimeException("repo type not provided!");
        }
        marketIRepository = MarketRepoFactory.createIRepository(repoType);
        this.marketIdCounter = marketIRepository.getObjects().size();
    }
    public static void setRepoType(RepoTypes rT) {
        repoType = rT;
    }

    public static MarketController getInstance() {
        if(instance == null) {
            instance = new MarketController();
        }
        return instance;
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
