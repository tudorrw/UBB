package org.example.repo;

import org.example.model.Portfolio;

import java.util.ArrayList;
import java.util.List;

public class PortfolioRepository implements IRepository<Portfolio> {
    private List<Portfolio> portfolios;

    public PortfolioRepository() {
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
    public void delete(Portfolio object) {
        portfolios.remove(object);
    }
}
