/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package JdbcDaoTests;

import DAO.JdbcDao;
import java.sql.Connection;
import nativeDB.MySQLConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

/**
 *
 * @author W10
 */
@TestInstance(Lifecycle.PER_CLASS)
public class AddTests {
    Connection cn;
    MySQLConnection sqlCn;
    JdbcDao jdbcDao;
    
    public AddTests() {
    }
    
    @BeforeAll
    public void setUpClass() {
        JdbcDao.init("jdbc:mysql://localhost:3306/jdbcdb", "root", "pass");
        jdbcDao = JdbcDao.instance();
    }
    
    @AfterAll
    public void tearDownClass() {
        JdbcDao.close();
    }
    
//    @Test
//    public void addSucursalFail() {
//        var sucursal = new Sucursal("4", "Programatically", new Lugar("100", "direccion", "ciudad"));
//        var rs = jdbcDao.addSucursal(sucursal);
//        
//        assertFalse(rs);
//    }
//    
//    @Test
//    @Disabled
//    public void addSucursal() {
//        var sucursal = new Sucursal("4", "Programatically", new Lugar("1", "direccion", "ciudad"));
//        var rs = jdbcDao.addSucursal(sucursal);
//        
//        assertTrue(rs);
//    }
}
