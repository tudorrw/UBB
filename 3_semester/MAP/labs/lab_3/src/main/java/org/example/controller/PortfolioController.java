package org.example.controller;

import org.example.model.Portfolio;
import org.example.repo.IRepository;

import java.time.LocalDateTime;
import java.util.List;

public class PortfolioController {
    private IRepository<Portfolio> repository;

    public PortfolioController(IRepository<Portfolio> repository) {
        this.repository = repository;
    }

    public boolean addPortfolio(int userId, int id, int cash, List<Integer> idStocks){
        if(!this.searchPortfolioBool(id)) {
            repository.save(new Portfolio(userId,id, cash,idStocks));
            return true;
        }
        else{
            return false;
        }
    }

    public boolean removePortfolio(int id){
        Portfolio search = this.searchPortfolio(id);
        if(search != null) {
            repository.delete(search);
            return true;
        }
        else{
            return false;
        }
    }

    public List<Portfolio> getAll(){
        return repository.getObjects();
    }

    public boolean searchPortfolioBool(int id){
        List<Portfolio> portfolios = repository.getObjects();
        for (Portfolio portfolio: portfolios) {
            if(portfolio.getId() == id){
                return true;
            }
        }
        return false;
    }

    public Portfolio searchPortfolio(int id){
        List<Portfolio> portfolios = repository.getObjects();
        for (Portfolio portfolio: portfolios) {
            if(portfolio.getId() == id){
                return portfolio;
            }
        }
        return null;
    }
}
