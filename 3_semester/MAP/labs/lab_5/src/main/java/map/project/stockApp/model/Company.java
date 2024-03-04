package map.project.stockApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import map.project.stockApp.utils.observer.Observable;
import map.project.stockApp.utils.observer.Observer;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity(name = "companies")
public class Company implements Observable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String name;
    private long capitalization;
    private long number_shares;
    private long number_shares_outstanding;
    @Transient
    private List<Observer> observers;

    public Company() {
        this.observers = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", capitalization=" + capitalization +
                ", numberShares=" + number_shares +
                "}";
    }

    //r trebui sa avem si ceva number of shares not bought ca sa nu se poata cumpara mai multe shares decat exista
    public Company(int id, String name, long capitalization, long number_shares, long numberSharesOutstanding) {
        this.number_shares_outstanding = numberSharesOutstanding;
        this.observers = new ArrayList<>();
        this.id = id;
        this.name = name;
        this.capitalization = capitalization;
        this.number_shares = number_shares;
    }

    public void setNumber_shares_outstanding(long number_shares_outstanding) {
        this.number_shares_outstanding = number_shares_outstanding;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setCapitalization(long capitalization) {
        this.capitalization = capitalization;
        this.notifyObserver();
    }


    public void setNumber_shares(long numberShares) {
        this.number_shares = numberShares;
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
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
