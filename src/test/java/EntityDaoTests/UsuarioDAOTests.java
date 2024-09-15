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
        int tipo_usuario = 1;
        TipoUsuario tipoUsuario = EntityDAOPool.instance().getTipoUsuarioDAO().getTypeUser(tipo_usuario);
        var rs = instance.getUsuarioDAO().create(new Usuario(0, tipoUsuario, "VegaTest2", "Vega1234"));
        System.out.println("ID User Created: " + rs);
        assertNotNull(rs);
    }
    
    // Get Users By ID or All Test (Read)
    @Test
    public void getById() {
        int id_user = 1;
        var rs = instance.getUsuarioDAO().getById(id_user);
        System.out.println("User With ID " + id_user + ": " + rs);
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
        // Se Crea el Objeto Tipo Usuario
        int tipo_usuario = 1;
        TipoUsuario tipoUsuario = EntityDAOPool.instance().getTipoUsuarioDAO().getTypeUser(tipo_usuario);
        // Se Crea el Objeto Usuario (Pasandole el Objeto TipoUsuario)
        var rs = instance.getUsuarioDAO().update(new Usuario (16, tipoUsuario, "vegaUpdate", "vegaUpdatePass"));
        System.out.println(rs);
        assertNotNull(rs);
    }
    
    // Delete Usuario Test
    @Test
    public void delete() {
        int id_delete = 15;
       var rs = instance.getUsuarioDAO().deleteById(id_delete);
        System.out.println("User With ID " + id_delete + "Deleted: " + rs);
        assertNotNull(rs);
    }
}
