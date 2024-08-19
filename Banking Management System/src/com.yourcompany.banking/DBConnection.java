package com.yourcompany.banking;

import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/banking";
    private static final String USER = "root";
    private static final String PASSWORD = "uv060203P";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
