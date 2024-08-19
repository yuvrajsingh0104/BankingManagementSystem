package com.yourcompany.banking;  // Ensure this matches the package structure

import java.sql.Connection;       // Import SQL Connection class
import java.sql.SQLException;     // Import SQLException class

public class TestConnection {
    public static void main(String[] args) {
        try {
            Connection conn = DBConnection.getConnection();  // Use DBConnection to get a connection
            if (conn != null) {
                System.out.println("Connection established successfully!");
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
    }
}
