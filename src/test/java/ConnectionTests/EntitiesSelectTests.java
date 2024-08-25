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

/**
 *
 * @author W10
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EntitiesSelectTests {
    private Connection cn;
    private MySQLConnection sqlCon;
    
    String url = "jdbc:mysql://localhost:3306/jdbcdb";
    String username = "root";
    String pass = "pass";
    
    public EntitiesSelectTests() {
    }
    
    @BeforeAll
    public void setUpClass() {
        sqlCon = new MySQLConnection(url, username, pass);
        cn = sqlCon.connection();
    }
    
    @AfterAll
    public void tearDownClass() throws IllegalStateException {
        sqlCon.close();
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    @Test
    public void selectLugar() throws SQLException {
        var stmt = cn.createStatement();
        
        String query = "SELECT * FROM lugares WHERE id=1;";
        
        var rs = stmt.executeQuery(query);
        rs.next();
        var id = rs.getString("id");
        
        assertEquals(id, "1");
        
    }
}
