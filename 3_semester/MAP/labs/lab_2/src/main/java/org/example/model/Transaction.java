package org.example.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Transaction {
    private int id;
    private int user_id;
    private int stock_id;
    private LocalDateTime date;

    public Transaction(int id, int user_id, int stock_id, LocalDateTime date) {
        this.id = id;
        this.user_id = user_id;
        this.stock_id = stock_id;
        this.date = date;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getStock_id() {
        return stock_id;
    }

    public void setStock_id(int stock_id) {
        this.stock_id = stock_id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
