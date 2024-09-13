/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package EntityDaoTests;

import DAO.EntityDAOPool;
import Records.TipoUsuario;
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
    
    // Create User Test
    @Test 
    public void create() {
        var rs = instance.getUsuarioDAO().create(new Usuario(14, 1, "vegaInsert", "1234"));
        System.out.println("ID User Created: " + rs);
        assertNotNull(rs);
    }
    
    // Get Users Test (Read)
    
    @Test
    public void select() {
        var rs = instance.getUsuarioDAO().getById(1);
        System.out.println(rs);
        assertNotNull(rs);
    }
    
    @Test
    public void getAll() {
        var rs = instance.getUsuarioDAO().getAll();
        System.out.println("Getting All Users");
        for (Usuario usr : rs) {
            System.out.println("User: " + usr);
        }
        assertNotNull(rs);
    }
    
    // Update Usuario Test 
    @Test
    public void update() {
        var rs = instance.getUsuarioDAO().update(new Usuario (13, 2, "vegaUpdate", "vegaUpdatePass"));
        System.out.println(rs);
        assertNotNull(rs);
    }
    
    // Delete Usuario Test
    @Test
    public void delete() {
       var rs = instance.getUsuarioDAO().deleteById(14);
        System.out.println(rs);
        assertNotNull(rs);
    }
}
