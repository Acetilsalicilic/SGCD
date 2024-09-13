/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package EntityDaoTests;

import DAO.EntityDAOPool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.TestInstance;

/**
 *
 * @author Vega
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ServicioDAOTest {
    
    public ServicioDAOTest() {
    }
    
    private EntityDAOPool instance;
    
    @BeforeAll
    public void setUpClass() {
        EntityDAOPool.init("jdbc:mysql://localhost:3306/lab4", "root", "");
        instance = EntityDAOPool.instance();
    }
    
    @AfterAll
    public static void tearDownClass() {
        EntityDAOPool.close();
    }
    
    @Test 
    public void tipoServicio(){
        var rs = instance.getServicioDAO().getTypeService(2);
        System.out.println(rs);
        assertNotNull(rs);
    }
    
}
