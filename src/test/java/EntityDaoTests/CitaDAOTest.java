/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package EntityDaoTests;

import DAO.EntityDAOPool;
import Records.Cita;
import Records.Medico;
import Records.Paciente;
import Records.Servicio;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.TestInstance;

/**
 *
 * @author Vega
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CitaDAOTest {
    
    public CitaDAOTest() {
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
    public void getAllCitas() {
        var rs = instance.getCitaDAO().getAllCitas(1);
        System.out.println(rs);
        assertNotNull(rs);
    }
    
    @Test 
    public void createCita() {
        
        Medico medico = EntityDAOPool.instance().getMedicoDAO().getById(1);
        Paciente paciente = EntityDAOPool.instance().getPacienteDAO().getById(1);
        Servicio servicio = EntityDAOPool.instance().getServicioDAO().getTypeService(1);
        
        var rs = instance.getCitaDAO().createCita(new Cita(0, medico, paciente, servicio, LocalDateTime.parse("2024-12-12T00:00")));
        System.out.println(rs);
        assertNotNull(rs);
    }
    
}
