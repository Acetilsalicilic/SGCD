/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package EntityDaoTests;

import DAO.EntityDAOPool;
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
public class UsuarioDAOTests {
    
    public UsuarioDAOTests() {
    }

    private EntityDAOPool instance;
    
    @BeforeAll
    public void setUpClass() {
        EntityDAOPool.init("jdbc:mysql://localhost:3306/lab4", "root", "pass");
        instance = EntityDAOPool.instance();
    }
    
    @AfterAll
    public void tearDownClass() {
        EntityDAOPool.close();
    }
    
    @Test
    public void select() {
        var rs = instance.getUsuarioDAO().getById(1);
        System.out.println(rs);
        assertNotNull(rs);
    }
}
