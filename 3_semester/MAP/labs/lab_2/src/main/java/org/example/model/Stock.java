package org.example.model;

public class Stock {
    private int id;
    private String name;
    private int price;
    private int companyId;
    private int marketId;

    public Stock(int id, String name, int price, int company_, int market_) {
        this.id = id;
        this.name = name;
        this.price = price;
        companyId = company_;
        marketId = market_;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int company_) {
        companyId = company_;
    }

    public int getmarket() {
        return marketId;
    }

    public void setMarketId(int market_) {
        marketId = market_;
    }
}
