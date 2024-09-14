/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package EntityDaoTests;

import DAO.EntityDAOPool;
import Records.Medico;
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
public class MedicoDAOTest {
    
    public MedicoDAOTest() {
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
    
    // Create Medico Test
    @Test
    public void createMedico() {
        int id_usuario = 10;
        var usuario = EntityDAOPool.instance().getUsuarioDAO().getById(id_usuario);
        var especialidad = EntityDAOPool.instance().getEspecialidadDAO().getTypeEspecialidad(1);
        var rs = instance.getMedicoDAO().createMedico(new Medico(0, usuario, especialidad, "VegaDocID4", "Vega"));
        System.out.println("ID Medico Created: " + rs);
        assertNotNull(rs);
    }
    
    // Get Medico By ID or All Test (Read)
    @Test
    public void getAllMedico() {
        var rs = instance.getMedicoDAO().getAll();
        System.out.println(rs);
        assertNotNull(rs);
    }
    
    @Test
    public void getMedicoById() {
        var rs = instance.getMedicoDAO().getById(1);
        System.out.println(rs);
        assertNotNull(rs);
    }
    
    // Find Medico By Name Test (Read)
    @Test
    public void getMedicoByName() {
        String nombre_medico = "Vega";
        var rs = instance.getMedicoDAO().getByNombre(nombre_medico);
        System.out.println("Medico With Name " + nombre_medico + " Found: " + rs);
        assertNotNull(rs);
    }
    
    // Find Medico By Speciality Test (Read)
    @Test
    public void getMedicoBySpeciality() {
        String speciality_desc = "Ortodoncia";
        var rs = instance.getMedicoDAO().getBySpeciality(speciality_desc);
        System.out.println("Medico(s) With Speciality " + speciality_desc + " Found: " + rs);
        assertNotNull(rs);
    }
    
    // Update Medico Test 
    @Test 
    public void updateMedico() {
        int id_usuario = 10;
        var usuario = EntityDAOPool.instance().getUsuarioDAO().getById(id_usuario);
        int id_especialidad = 1;
        var especialidad = EntityDAOPool.instance().getEspecialidadDAO().getTypeEspecialidad(id_especialidad);
        int id_medico = 2;
        var medico = EntityDAOPool.instance().getMedicoDAO().getById(id_medico);
        var rs = instance.getMedicoDAO().updateMedico(new Medico(id_medico, usuario, especialidad, "Fernando", medico.apellidos_medico()));
        System.out.println("Status Update: " + rs);
        assertEquals(1, 1);
    }
    
    // Delete Medico Test
    @Test
    public void deleteMedico() {
        int id_medico = 4; 
        var rs = instance.getMedicoDAO().deleteMedicoById(id_medico);
        System.out.println("Medico With ID " + id_medico + " Deleted Status: " + rs);
        assertNotNull(rs);
    }
}
