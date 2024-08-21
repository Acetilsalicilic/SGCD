/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nativeDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author W10
 */
public class MySQLConnection {
    private String url = "jdbc:mysql://localhost:3306/prueba";
    public MySQLConnection() throws IllegalStateException {
        System.out.println("Connecting to mysql...");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot load driver", e);
        }
        try (Connection cn = DriverManager.getConnection(url, "root", "pass")) {
            System.out.println("Connection established!");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect", e);
        }
    }
}
