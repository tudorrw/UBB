package org.example.model;

import org.example.utils.observer.Observer;
import org.example.utils.strategy.GrowthStockValuationStrategy;
import org.example.utils.strategy.StockValuationStrategy;

public class Stock implements Observer {
    private int id;
    private String name;
    private float price;
    private Company company;
    private Market market;

    private StockValuationStrategy valuationStrategy;

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", company=" + company.getName() +
                ", market=" + market.getName() +
                '}';
    }

    public Stock(int id, String name, Company company_, Market market_) {
        this.id = id;
        this.name = name;
        this.company = company_;
        this.market = market_;
        this.price = (float) company.getCapitalization()/company.getNumberShares();
        this.valuationStrategy = new GrowthStockValuationStrategy();
        this.company.registerObserver(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    private void setPrice() {
        this.price = (float) this.company.getCapitalization()/this.company.getNumberShares();
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company_) {
        company.removeObserver(this);
        company = company_;
        company.registerObserver(this);
    }

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market_) {
        market = market_;
    }

    public void setValuationStrategy(StockValuationStrategy valuationStrategy) {
        this.valuationStrategy = valuationStrategy;
    }

    public double calculateValue() {
        return valuationStrategy.calculatePossibleProfit(this);
    }

    @Override
    public void update() {
        this.setPrice();
    }
}

