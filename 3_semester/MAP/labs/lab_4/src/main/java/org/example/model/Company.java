package org.example.model;

import org.example.utils.observer.Observable;
import org.example.utils.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class Company implements Observable {
    private int id;
    private String name;
    private long capitalization;
    private long numberShares;

    private List<Observer> observers;

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", capitalization=" + capitalization +
                ", numberShares=" + numberShares +
                '}';
    }

    public Company(int id, String name, long capitalization, long numberShares) {
        this.observers = new ArrayList<>();
        this.id = id;
        this.name = name;
        this.capitalization = capitalization;
        this.numberShares = numberShares;
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

    public long getCapitalization() {
        return capitalization;
    }


    public void setCapitalization(long capitalization) {
        this.capitalization = capitalization;
        this.notifyObserver();
    }

    public long getNumberShares() {
        return numberShares;
    }

    public void setNumberShares(long numberShares) {
        this.numberShares = numberShares;
        this.notifyObserver();
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserver() {
        for(Observer observer: observers){
            observer.update();
        }
    }
}
