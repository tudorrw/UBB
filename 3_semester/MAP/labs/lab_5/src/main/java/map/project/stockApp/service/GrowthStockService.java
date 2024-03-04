package map.project.stockApp.service;

import map.project.stockApp.model.Company;
import map.project.stockApp.model.GrowthStock;
import map.project.stockApp.model.Market;
import map.project.stockApp.repo.RepoTypes;
import map.project.stockApp.repo.springRepo.GrowthStockRepositorySpring;
import map.project.stockApp.utils.memento.Caretaker;
import map.project.stockApp.utils.memento.GrowthStockMemento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GrowthStockService {
    @Autowired
    private GrowthStockRepositorySpring repository;
    private static RepoTypes repoType;
    private static GrowthStockService instance;
    private Caretaker caretaker;

    private GrowthStockService() {
        this.caretaker = Caretaker.getInstance();
    }

    public static GrowthStockService getInstance() {
        if (instance == null) {
            instance = new GrowthStockService();
        }
        return instance;
    }

    public static void setRepoType(RepoTypes rT) {
        repoType = rT;
    }


    public boolean addGrowthStockObject(GrowthStock growthStock) {
        if (!this.repository.existsById(growthStock.getId()) && !this.repository.existsGrowthStockByName(growthStock.getName())) {
            repository.save(growthStock);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeGrowthStock(int id) {
        GrowthStock search = this.searchGrowthStock(id);
        if (search != null) {
            caretaker.removeMementoById(id);
            repository.delete(search);
            return true;
        } else {
            return false;
        }
    }


    public List<GrowthStock> getAll() {
        return repository.findAll();
    }

    public boolean searchGrowthStockBool(int id) {
        Optional<GrowthStock> growthStock = repository.findById(id);
        if (growthStock != null) {
            return true;
        }
        return false;
    }

    public GrowthStock searchGrowthStock(int id) {
        Optional<GrowthStock> growthStock = repository.findById(id);
        if (growthStock != null) {
            return growthStock.orElse(null);
        }

        return null;
    }

    public void updateGrowthStock(int id, String newName, Company newCompany, Market newMarket, int newGrowthRate) {
        GrowthStock growthStock = repository.findById(id).orElse(null);
        if (growthStock != null) {
            caretaker.addMemento(growthStock.createGrowthMemento());
            System.out.println(caretaker.getMementos());
            growthStock.setName(newName);
            growthStock.setCompany(newCompany);
            growthStock.setMarket(newMarket);
            growthStock.setGrowth_rate(newGrowthRate);
            repository.save(growthStock);
        }
    }

    public void undoUpdate(int id) {
        GrowthStockMemento growthStockMemento = (GrowthStockMemento) caretaker.getMementoById(id);
        if (growthStockMemento != null) {
            GrowthStock growthStock = repository.findById(id).orElse(null);
            if (growthStock != null) {
                growthStock.restoreFromMemento(growthStockMemento);
                repository.save(growthStock);
            }
        }
    }
}
