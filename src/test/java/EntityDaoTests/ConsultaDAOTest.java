/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package EntityDaoTests;

import DAO.EntityDAOPool;
import Records.Consulta;
import Records.Medico;
import Records.Paciente;
import Records.Servicio;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
public class ConsultaDAOTest {
    
    public ConsultaDAOTest() {
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
    
    // Create Consulta Test
    @Test
    public void createConsulta() {
        int id_medico = 1;
        int id_paciente = 2;
        int id_servicio = 1;
        Medico medico = EntityDAOPool.instance().getMedicoDAO().getById(id_medico);
        Paciente paciente = EntityDAOPool.instance().getPacienteDAO().getById(id_paciente);
        Servicio servicio = EntityDAOPool.instance().getServicioDAO().getTypeService(id_servicio);
        
        var rs = instance.getConsultaDAO().createConsulta(new Consulta(0, medico, paciente, servicio, LocalDateTime.parse("2024-12-12T15:30")));
        System.out.println("Consulta With ID " + rs + " Created Succesfully!");
        assertNotNull(rs);
    }
    
    
    // Get Consultas By ID or All Test (Read)
    @Test 
    public void getById() {
        int id_consulta = 1; 
        var rs = instance.getConsultaDAO().getById(id_consulta);
        System.out.println("Consulta With ID " + id_consulta + " Found: " + rs);
        assertNotNull(rs);
    }
    
    @Test
    public void getAll() {
        int id_usuario = 1;
        var rs = instance.getConsultaDAO().getAllConsultas(id_usuario);
        System.out.println("Consultas Found For User ID " + id_usuario + ": " + rs);
        assertNotNull(rs);
    }
    
    // Check Availability of Schedule 
    @Test
    public void horasDisponibles() {
        int id_medico = 1;
        LocalDate fechaPrueba = LocalDate.of(2024, 12, 12);
        List<String> horasDisponibles = EntityDAOPool.instance().getConsultaDAO().horasDisponiblesCitas(id_medico, fechaPrueba);
        System.out.println("Horas Disponibles: " + horasDisponibles);
        assertNotNull(horasDisponibles);
    }
    
    // Update Consulta 
    @Test
    public void updateConsulta() {
        int id_consulta = 3;
        String date_cita = "2024-12-12 17:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(date_cita, formatter);
        var consulta = EntityDAOPool.instance().getConsultaDAO().getById(id_consulta);
        var rs = instance.getConsultaDAO().updateConsulta(new Consulta(id_consulta, consulta.medico(), consulta.paciente(), consulta.servicio(), dateTime));
        System.out.println("Consulta With ID " + id_consulta + " Updated Status: " + rs);
        assertNotNull(rs);
    }
    
    // Delete Consulta By ID
    @Test
    public void deleteConsulta() {
        int id_consulta = 3;
        var rs = instance.getConsultaDAO().deleteById(id_consulta);
        System.out.println("Consulta With ID " + id_consulta + " Deleted Status " + rs);
        assertNotNull(rs);
    }
    
}
