package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection;

    private DBConnection() {}
    public static void setConnection(String url, String user, String passwd) throws SQLException {
        connection = DriverManager.getConnection(url, user, passwd);
    }

    public static Connection getConnection() {
        if(connection == null) {
            throw new RuntimeException("No database connection provided!");
        }
        return connection;
    }
}
