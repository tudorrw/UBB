package org.example.model;

import java.time.LocalDateTime;

public class Transaction {
    private int id;
    private User user;
    private Stock stock;
    private LocalDateTime date;

    public Transaction(int id, User user, Stock stock, LocalDateTime date) {
        this.id = id;
        this.user = user;
        this.stock = stock;
        this.date = date;
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

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
