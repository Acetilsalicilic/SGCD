/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package EntityDaoTests;

import DAO.EntityDAOPool;
import Records.Paciente;
import Records.Usuario;
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
public class PacienteDAOTests {
    private EntityDAOPool instance;
    
    public PacienteDAOTests() {
    }
    
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
    public void getByName() {
        var dao = instance.getPacienteDAO();
        
        var rs = dao.getByNombre("ast");
        assertNotNull(rs);
        
        assertFalse(rs.isEmpty());
        
        for (var pac : rs) {
            System.out.println("paciente: " + pac);
        }
    }
    
    @Test
    @Disabled
    public void create() {
        var dao = instance.getPacienteDAO();
        
        var paciente = new Paciente(
                5,
                "jast",
                "test test",
                "12345678890",
                "direccion",
                new Usuario(
                        2,
                        "asd",
                        "asd",
                        "admin"
                )
        );
        
        var rs = dao.create(paciente);
        
        assertEquals(1, rs);
    }
}
