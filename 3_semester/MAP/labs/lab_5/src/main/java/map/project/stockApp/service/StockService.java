package map.project.stockApp.service;


import map.project.stockApp.model.Company;
import map.project.stockApp.model.Market;
import map.project.stockApp.model.Stock;
import map.project.stockApp.repo.springRepo.StockRepositorySpring;
import map.project.stockApp.utils.memento.Caretaker;
import map.project.stockApp.utils.memento.StockMemento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {
    @Autowired
    private StockRepositorySpring repository;
    private Caretaker caretaker;

    public StockService() {
        this.caretaker = Caretaker.getInstance();
    }

    public boolean addStock(int id, String name, Company company, Market market) {
        if (!this.searchStockBool(id)) {
            repository.save(new Stock(id, name, company, market));
            return true;
        } else {
            return false;
        }
    }

    public boolean removeStock(int id) {
        Stock search = this.searchStock(id);
        if (search != null) {
            repository.delete(search);
            return true;
        } else {
            return false;
        }
    }


    public boolean searchStockBool(int id) {
        Stock stock = repository.findById(id).orElse(null);
        if (stock.getId() == id) {
            return true;
        }
        return false;
    }

    public Stock searchStock(int id) {
        Stock stock = repository.findById(id).orElse(null);
        if (stock.getId() == id) {
            return stock;
        }
        return null;
    }

    public Stock findStock(String name) {
        return repository.findStockByName(name);
    }


    public boolean addSimpleStockObject(Stock stock) {
        if (!this.repository.existsById(stock.getId()) && !repository.existsStockByName(stock.getName())) {
            repository.save(stock);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeSimpleStock(int id) {
        Stock search = this.searchStock(id);
        if (search != null) {
            caretaker.removeMementoById(id);
            repository.delete(search);
            return true;
        } else {
            return false;
        }
    }


    public List<Stock> getAll() {
        return repository.findAll();
    }


    public void updateSimpleStock(int id, String newName, Company newCompany, Market newMarket) {
        Stock stock = repository.findById(id).orElse(null);
        if (stock != null) {
            caretaker.addMemento(stock.createMemento());
            System.out.println(caretaker.getMementos());
            stock.setName(newName);
            stock.setCompany(newCompany);
            stock.setMarket(newMarket);
            repository.save(stock);
        }
    }

    public void undoUpdate(int id) {
        StockMemento stockMemento = (StockMemento) caretaker.getMementoById(id);
        if (stockMemento != null) {
            Stock stock = repository.findById(id).orElse(null);
            if (stock != null) {
                stock.restoreFromMemento(stockMemento);
                repository.save(stock);
            }
        }
    }
}
