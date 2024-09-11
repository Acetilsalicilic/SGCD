/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package EntityDaoTests;

import DAO.EntityDAOPool;
import Records.Usuario;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

// TODO: CAMBIAR NOMBRE DB AL HACER PRUEBAS

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
        EntityDAOPool.init("jdbc:mysql://localhost:3306/lab4", "root", "");
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
    
//    @Test
//    @Disabled
//    public void insert() {
//        var dao = instance.getUsuarioDAO();
//        
//        int result = dao.create(new Usuario(2, 2, "vega2", "veguita"));
//        
//        assertEquals(result, 1);
//    }
    
    @Test
    public void update() {
        var dao = instance.getUsuarioDAO();
        
        int result = dao.update(new Usuario(1, 2, "vega1Act22.2", "12344pas"));
        
        assertEquals(1, result);
    }
    
    @Test
    public void getAll() {
        var dao = instance.getUsuarioDAO();
        
        var rs = dao.getAll();
        
        assertNotNull(rs);
        
        System.out.println("User from all");
        for (Usuario usr : rs) {
            System.out.println("user: " + usr);
        }
    }
    
}
