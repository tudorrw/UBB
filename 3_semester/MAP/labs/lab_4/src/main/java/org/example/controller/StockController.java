package org.example.controller;


import org.example.model.Company;
import org.example.model.Market;
import org.example.model.Stock;
import org.example.repo.IRepository;

import java.util.List;

public class StockController {
    private IRepository<Stock> repository;

    public StockController(IRepository<Stock> repository) {
        this.repository = repository;
    }

    public boolean addStock(int id, String name, Company company, Market market){
        if(!this.searchStockBool(id)) {
            repository.save(new Stock(id, name, company,market));
            return true;
        }
        else{
            return false;
        }
    }

    public boolean removeStock(int id){
        Stock search = this.searchStock(id);
        if(search != null) {
            repository.delete(search);
            return true;
        }
        else{
            return false;
        }
    }

    public List<Stock> getAll(){
        return repository.getObjects();
    }

    public boolean searchStockBool(int id){
        List<Stock> stocks = repository.getObjects();
        for (Stock stock: stocks) {
            if(stock.getId() == id){
                return true;
            }
        }
        return false;
    }

    public Stock searchStock(int id){
        List<Stock> stocks = repository.getObjects();
        for (Stock stock: stocks) {
            if(stock.getId() == id){
                return stock;
            }
        }
        return null;
    }
}
