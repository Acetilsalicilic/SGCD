/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package ConnectionTests;

import nativeDB.MySQLConnection;
import org.junit.jupiter.api.Test;

/**
 *
 * @author W10
 */
public class ConnectionTests {
    String url = "jdbc:mysql://localhost:3306/lab4";
    String username = "root";
    String pass = "pass";
    
    public ConnectionTests() {
    }

    @Test
    public void connect() {
        new MySQLConnection(url, username, pass);
    }
}
