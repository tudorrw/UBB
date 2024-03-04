package org.example.repo.inMemoryRepo;

import org.example.model.Portfolio;
import org.example.repo.IRepository;

import java.util.ArrayList;

public class PortfolioRepositoryIM implements IRepository<Portfolio> {
    private ArrayList<Portfolio> portfolios;
    private static PortfolioRepositoryIM instance;

    private PortfolioRepositoryIM() {
        this.portfolios = new ArrayList<>();
    }

    @Override
    public ArrayList<Portfolio> getObjects() {
        return portfolios;
    }

    @Override
    public void save(Portfolio entity) {
        portfolios.add(entity);
    }

    @Override
    public void update(Portfolio entity) {

    }

    @Override
    public void delete(Portfolio object) {
        portfolios.remove(object);
    }


    public static PortfolioRepositoryIM getInstance() {
        if (instance == null) {
            instance = new PortfolioRepositoryIM();
        }
        return instance;
    }
}
