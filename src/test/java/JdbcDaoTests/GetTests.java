/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package JdbcDaoTests;

import DAO.JdbcDao;
import Records.Lugar;
import java.sql.Connection;
import nativeDB.MySQLConnection;
import org.junit.jupiter.api.AfterAll;
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
public class GetTests {
    Connection cn;
    MySQLConnection sqlCn;
    JdbcDao jdbcDao;
    
    public GetTests() {
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
    
    @Test
    public void getLugar() {
        var newLugar = jdbcDao.getLugar("1");
        assertEquals(newLugar, new Lugar("1", "ciudad", "direccion"));
    }
    
    @Test
    public void getMedico() {
        var newMedico = jdbcDao.getMedico("1");
        System.out.println(newMedico);
        
        assertNotNull(newMedico);
    }
    
    @Test
    public void getSucursal() {
        var newSucursal = jdbcDao.getMedico("1");
        System.out.println(newSucursal);
        
        assertNotNull(newSucursal);
    }
}
