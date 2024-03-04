package org.example.repo;

import org.example.model.Portfolio;
import org.example.model.ValueStock;

import java.util.ArrayList;
import java.util.List;

public class PortfolioRepository implements IRepository<Portfolio>{
    private List<Portfolio> portfolios;
    private static PortfolioRepository instance;

    private PortfolioRepository() {
        this.portfolios = new ArrayList<>();
    }

    @Override
    public List<Portfolio> getObjects() {
        return portfolios;
    }

    @Override
    public void save(Portfolio entity) {
        portfolios.add(entity);
    }

    @Override
    public void update(Portfolio entity, String action) {

    }

    @Override
    public void delete(Portfolio object) {
        portfolios.remove(object);
    }


    public static PortfolioRepository getInstance() {
        if (instance == null) {
            instance = new PortfolioRepository();
        }
        return instance;
    }
}
