/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nativeDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author W10
 */
public class MySQLConnection {
    private String url;
    private String defaultUrl = "jdbc:mysql://localhost:3306/jdbcdb";
    Connection cn;
    public MySQLConnection(String url, String username, String password) throws IllegalStateException {
        System.out.println("Connecting to mysql...");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot load driver", e);
        }
        try {
            cn = DriverManager.getConnection(url, username, password);
            System.out.println("Connection established!");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect", e);
        }
    }
    
    public MySQLConnection() {
        System.out.println("Connecting to mysql with default values...");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot load driver", e);
        }
        try (Connection cn = DriverManager.getConnection(url,"root", "pass")) {
            System.out.println("Connection established!");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect", e);
        }
        
        
    }
    
    public Connection connection() {
        return cn;
    }
    
    public void close() {
        try {
            cn.close();
        } catch (SQLException ex) {
            System.out.println("Impossible to close connection");
        }
    }
}
