package org.example.controller;

import java.util.ArrayList;

public interface IController<T> {
    void add(T object);

    void update(T object);
    void delete (T object);

    ArrayList<T> getAll();
}
