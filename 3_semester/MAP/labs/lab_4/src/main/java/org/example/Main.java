package org.example;

import org.example.database.DBConnection;
import org.example.view.View;

import java.sql.*;

public class Main {
    public static void main(String[] args) {

        try {
            DBConnection.setConnection("jdbc:mysql://localhost:3306/stockApp", "dev", "MAPstockDB123");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        View view = View.getInstance();
        view.run();
    }


}