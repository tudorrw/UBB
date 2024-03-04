package map.project.stockApp.service;

import map.project.stockApp.model.Company;
import map.project.stockApp.model.HybridStock;
import map.project.stockApp.model.Market;
import map.project.stockApp.repo.RepoTypes;
import map.project.stockApp.repo.springRepo.HybridStockRepositorySpring;
import map.project.stockApp.utils.memento.Caretaker;
import map.project.stockApp.utils.memento.HybridStockMemento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HybridStockService {
    @Autowired
    private HybridStockRepositorySpring repository;
    private static RepoTypes repoType;
    private static HybridStockService instance;
    private Caretaker caretaker;

    private HybridStockService() {
        this.caretaker = Caretaker.getInstance();
    }

    public static HybridStockService getInstance() {
        if (instance == null) {
            instance = new HybridStockService();
        }
        return instance;
    }

    public static void setRepoType(RepoTypes rT) {
        repoType = rT;
    }


    public boolean addHybridStockObject(HybridStock hybridStock) {
        if (!this.repository.existsById(hybridStock.getId()) && !this.repository.existsHybridStockByName(hybridStock.getName())) {
            repository.save(hybridStock);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeHybridStock(int id) {
        HybridStock search = this.searchHybridStock(id);
        if (search != null) {
            caretaker.removeMementoById(id);
            repository.delete(search);
            return true;
        } else {
            return false;
        }
    }


    public List<HybridStock> getAll() {
        return repository.findAll();
    }

    public boolean searchHybridStockBool(int id) {
        HybridStock hybridStock = repository.findById(id).orElse(null);
        if (hybridStock.getId() == id) {
            return true;
        }
        return false;
    }

    public HybridStock searchHybridStock(int id) {
        HybridStock hybridStock = repository.findById(id).orElse(null);
        if (hybridStock.getId() == id) {
            return hybridStock;
        }
        return null;
    }

    public void updateHybridStock(int id, String newName, Company newCompany, Market newMarket, int newGrowthRate, double newDividendRate) {
        HybridStock hybridStock = repository.findById(id).orElse(null);
        if (hybridStock != null) {
            caretaker.addMemento(hybridStock.createHybridMemento());
            System.out.println(caretaker.getMementos());
            hybridStock.setName(newName);
            hybridStock.setCompany(newCompany);
            hybridStock.setMarket(newMarket);
            hybridStock.setGrowth_rate(newGrowthRate);
            repository.save(hybridStock);
        }
    }

    public void undoUpdate(int id) {
        HybridStockMemento hybridStockMemento = (HybridStockMemento) caretaker.getMementoById(id);
        if (hybridStockMemento != null) {
            HybridStock hybridStock = repository.findById(id).orElse(null);
            if (hybridStock != null) {
                hybridStock.restoreFromMemento(hybridStockMemento);
                repository.save(hybridStock);
            }
        }
    }
}
