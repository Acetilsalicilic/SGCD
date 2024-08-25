/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package ConnectionTests;

import java.sql.Connection;
import java.sql.SQLException;
import nativeDB.MySQLConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

/**
 *
 * @author W10
 */
@TestInstance(Lifecycle.PER_CLASS)
public class EntitiesInsertTests {
    private Connection cn;
    private MySQLConnection sqlCon;
    
    String url = "jdbc:mysql://localhost:3306/jdbcdb";
    String username = "root";
    String pass = "pass";
    
    public EntitiesInsertTests() {
    }
    
    @BeforeAll
    public void setUpClass() {
        sqlCon = new MySQLConnection(url, username, pass);
        cn = sqlCon.connection();
    }
    
    @AfterAll
    public void tearDownClass() {
        sqlCon.close();
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    @Test
    public void insertLugar() throws SQLException {
        var stmt = cn.createStatement();
        String query = "INSERT INTO lugares VALUES ('1', 'ciudad', 'direccion');";
        
        var rs = stmt.executeUpdate(query);
    }
}
