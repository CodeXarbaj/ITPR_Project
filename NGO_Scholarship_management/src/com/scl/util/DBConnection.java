package com.scl.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    
    // Initialising database name, username aur password n variable
    private static final String URL = "jdbc:mysql://localhost:3306/scholarship_management"; 
    private static final String USER = "root"; 
    private static final String PASSWORD = "Arbaj@8948"; // SQL Password

    public static Connection getConnection() {
        Connection con = null;
        try {
            // Driver Loading
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Connection establishing
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            
        } catch (Exception e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
        }
        return con;
    }
}