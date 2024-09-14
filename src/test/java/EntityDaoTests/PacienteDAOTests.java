/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package EntityDaoTests;

import DAO.EntityDAOPool;
import Records.Paciente;
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
public class PacienteDAOTests {
    private EntityDAOPool instance;
    
    public PacienteDAOTests() {
    }
    
    @BeforeAll
    public void setUpClass() {
        EntityDAOPool.init("jdbc:mysql://localhost:3306/lab4", "root", "");
        instance = EntityDAOPool.instance();
    }
    
    @AfterAll
    public void tearDownClass() {
        EntityDAOPool.close();
    }
    
    // Create Paciente Test
    @Test
    public void createPaciente() {
        int id_usuario = 7;
        var usuario = EntityDAOPool.instance().getUsuarioDAO().getById(id_usuario);
        var rs = instance.getPacienteDAO().createPaciente(new Paciente(0, usuario, "Vicky", "LaNievePica", "1234567890", "HastaLaVerga"));
        System.out.println("ID Paciente Created: " + rs);
        assertNotNull(rs);
    }
    
    // Get Pacinete By ID or All Test (Read)
    @Test
    public void getPacienteById() {
        int id_paciente = 4;
        var rs = instance.getPacienteDAO().getById(id_paciente);
        System.out.println("Paciente With ID " + id_paciente + ": " + rs);
        assertNotNull(rs);
    }
    
    @Test
    public void getAllPacientes() {
        var rs = instance.getPacienteDAO().getAll();
        System.out.println(rs);
        assertNotNull(rs);
    }
    
    // Get Paciente By Name Test (Read)
    @Test 
    public void getPacienteByName() {
        String nombre_paciente = "Mike";
        var rs = instance.getPacienteDAO().getByNombre(nombre_paciente);
        System.out.println("Paciente With Name " + nombre_paciente + "Found: " + rs);
        assertNotNull(rs);
    }
    
    // Update Paciente Test
    @Test
    public void updatePaciente() {
        int id_usuario = 3;
        var usuario = EntityDAOPool.instance().getUsuarioDAO().getById(id_usuario);
        int id_paciente = 2;
        var paciente = EntityDAOPool.instance().getPacienteDAO().getById(id_paciente);
        var rs = instance.getPacienteDAO().updatePaciente(new Paciente(id_paciente, usuario, "VegaTestUpdate", paciente.apellidos(), paciente.telefono(), paciente.direccion()));
        System.out.println(rs);
        assertNotNull(rs);
    }
    
    // Delete Paciente Test 
    @Test
    public void deletePaciente() {
        int id_paciente = 5;
        var rs = instance.getPacienteDAO().deletePacienteById(id_paciente);
        System.out.println("Paciente With ID " + id_paciente + " Deleted Status: " + rs);
        assertNotNull(rs);
    }
    
}
