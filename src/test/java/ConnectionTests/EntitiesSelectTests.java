/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package ConnectionTests;

import java.sql.Connection;
import nativeDB.MySQLConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;

/**
 *
 * @author W10
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EntitiesSelectTests {
    private Connection cn;
    private MySQLConnection sqlCon;
    
    String url = "jdbc:mysql://localhost:3306/lab4";
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
    
//    @Test
//    public void selectLugar() throws SQLException {
//        var stmt = cn.createStatement();
//        
//        String query = "SELECT * FROM lugares WHERE id=1;";
//        
//        var rs = stmt.executeQuery(query);
//        rs.next();
//        var id = rs.getString("id");
//        
//        assertEquals(id, "1");
//        
//    }
    
//    @Test
//    public void selectLugarParsed() throws SQLException {
//        var stmt = cn.createStatement();
//        
//        String query = "SELECT * FROM lugares WHERE id=1;";
//        
//        var rs = stmt.executeQuery(query);
//        rs.next();
//        
//        // Parse lugar
//        
//        String id, direccion, ciudad;
//        
//        id = rs.getString("id");
//        direccion = rs.getString("direccion");
//        ciudad = rs.getString("ciudad");
//        
//        Lugar parsedLugar = new Lugar(id, direccion, ciudad);
//        
//        assertEquals(parsedLugar, new Lugar("1", "ciudad", "direccion"));
//    }
    
//    @Test
//    public void selectSucursalParsed() throws SQLException {
//        var st = cn.createStatement();
//        int index = 1;
//        
//        String query = "SELECT sucursales.id AS suc_id, sucursales.nombre, lugares.id AS lug_id, lugares.ciudad, lugares.direccion FROM sucursales INNER JOIN lugares ON sucursales.lugar_id=lugares.id WHERE sucursales.id="+index+";";
//        
//        var rs = st.executeQuery(query);
//        
//        rs.next();
//        
//        String suc_id, nombre, lug_id, ciudad, direccion;
//        
//        suc_id = rs.getString("suc_id");
//        nombre = rs.getString("nombre");
//        lug_id = rs.getString("lug_id");
//        ciudad = rs.getString("ciudad");
//        direccion = rs.getString("direccion");
//        
//        Sucursal parsedSucursal = new Sucursal(suc_id, nombre, new Lugar(lug_id, ciudad, direccion));
//        
//        assertEquals(parsedSucursal, new Sucursal("1", "sucursal 1", new Lugar("1", "direccion", "ciudad")));
//    }
}
