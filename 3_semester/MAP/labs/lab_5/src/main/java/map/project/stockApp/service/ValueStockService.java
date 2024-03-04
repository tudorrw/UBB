package map.project.stockApp.service;

import map.project.stockApp.model.Company;
import map.project.stockApp.model.Market;
import map.project.stockApp.model.ValueStock;
import map.project.stockApp.repo.RepoTypes;
import map.project.stockApp.repo.springRepo.ValueStockRepositorySpring;
import map.project.stockApp.utils.memento.Caretaker;
import map.project.stockApp.utils.memento.ValueStockMemento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValueStockService {
    @Autowired
    private ValueStockRepositorySpring repository;
    private static RepoTypes repoType;
    private static ValueStockService instance;

    private Caretaker caretaker;

    private ValueStockService() {
        this.caretaker = Caretaker.getInstance();
    }

    public static ValueStockService getInstance() {
        if (instance == null) {
            instance = new ValueStockService();
        }
        return instance;
    }

    public static void setRepoType(RepoTypes rT) {
        repoType = rT;
    }

    public boolean addValueStock(int id, String name, Company company, Market market, double dividend) {
        if (!this.searchValueStockBool(id)) {
            repository.save(new ValueStock(id, name, company, market, dividend));
            return true;
        } else {
            return false;
        }
    }

    public boolean addValueStockObject(ValueStock valueStock) {
        if (!this.repository.existsById(valueStock.getId())) {
            repository.save(valueStock);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeValueStock(int id) {
        ValueStock search = this.searchValueStock(id);
        if (search != null) {
            caretaker.removeMementoById(id);
            repository.delete(search);
            return true;
        } else {
            return false;
        }
    }

    public List<ValueStock> getAll() {
        return repository.findAll();
    }

    public boolean searchValueStockBool(int id) {
        ValueStock valueStock = repository.findById(id).orElse(null);
        if (valueStock != null) {
            return true;
        }
        return false;
    }

    public ValueStock searchValueStock(int id) {
        ValueStock valueStock = repository.findById(id).orElse(null);
        if (valueStock != null) {
            return valueStock;
        }
        return null;
    }


    public void updateValueStock(int id, String newName, Company newCompany, Market newMarket, double newDividentRate) {
        ValueStock valueStock = repository.findById(id).orElse(null);
        if (valueStock != null) {
            caretaker.addMemento(valueStock.createValueMemento());
            valueStock.setName(newName);
            valueStock.setCompany(newCompany);
            valueStock.setMarket(newMarket);
            valueStock.setDividend_rate(newDividentRate);
            repository.save(valueStock);
        }
    }


    public void undoUpdate(int id) {
        ValueStockMemento valueStockMemento = (ValueStockMemento) caretaker.getMementoById(id);
        if (valueStockMemento != null) {
            ValueStock valueStock = repository.findById(id).orElse(null);
            if (valueStock != null) {
                valueStock.restoreFromMemento(valueStockMemento);
            }
        }
    }
}
