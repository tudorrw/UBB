package org.example.utils.observer;

import org.example.model.Company;

public interface Observable {
    public void registerObserver(Observer observer);
    public void removeObserver(Observer observer);
    public void notifyObserver();
}
