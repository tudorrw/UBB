package org.example.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IDatabase<T> {
    Connection connection = DBConnection.getConnection();
    void add(T Entity);
    void remove(T Entity);
    void update(T Entity);
    ArrayList<T> getObjects();
}
