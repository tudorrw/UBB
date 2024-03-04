package org.example.model;

import java.util.List;

public class Portfolio {
    private int id;
    private User user;
    private double cash;
    private List<Stock> stocks;

    public Portfolio(int id, User user, double cash, List<Stock> stocks) {
        this.id = id;
        this.user = user;
        this.cash = cash;
        this.stocks = stocks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }
}