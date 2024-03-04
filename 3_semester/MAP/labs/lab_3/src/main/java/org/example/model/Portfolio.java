package org.example.model;

import java.util.List;

public class Portfolio {
    private int userId;
    private int id;
    private int cash;
    private List<Integer> idStocks;

    public Portfolio(int userId, int id, int cash, List<Integer> idStocks) {
        this.userId = userId;
        this.id = id;
        this.cash = cash;
        this.idStocks = idStocks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public List<Integer> getIdStocks() {
        return idStocks;
    }

    public void setIdStocks(List<Integer> idStocks) {
        this.idStocks = idStocks;
    }
}