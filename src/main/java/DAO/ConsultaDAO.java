/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Records.Consulta;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vega
 */
public class ConsultaDAO extends AbstractEntityDAO {
    
    public ConsultaDAO(Connection con) {
        super(con);
    }
    
    // Create Consulta
    public int createConsulta(Consulta consulta) {
        return inStatementUpdate((st) -> {
            String createConsultaQuery = "INSERT INTO consulta (id_medico, id_paciente, id_servicio, fecha_consulta) VALUES (?, ?, ?, ?);";
            try (PreparedStatement createConsultaStmt = st.getConnection().prepareStatement(createConsultaQuery, Statement.RETURN_GENERATED_KEYS)) {
                createConsultaStmt.setInt(1, consulta.medico().id_medico());
                createConsultaStmt.setInt(2, consulta.paciente().id_paciente());
                createConsultaStmt.setInt(3, consulta.servicio().id_servicio());
                createConsultaStmt.setTimestamp(4, Timestamp.valueOf(consulta.fecha_consulta()));
                
                int affectedRows = createConsultaStmt.executeUpdate();
                
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = createConsultaStmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            return generatedKeys.getInt(1); // ID Consulta Creada
                        } else  {
                            return -1; // No Se Genero Un ID
                        }
                    } 
                } else  {
                    return -1;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return -1; // Error SQL
            }
        });
    }
    
    // Methods to Check Avilability of Schedule
    public List<String> horasDisponiblesCitas(Integer id_medico, LocalDate fecha_cita) {
        return (List<String>) inStatementQuery((st) -> {
            List<String> horasOcupadas = new ArrayList<>();
            List<String> horasDisponibles = new ArrayList<>();
            String horasDisponiblesQuery = 
                "SELECT fecha_consulta " + 
                "FROM consulta " + 
                "WHERE id_medico = ? AND DATE(fecha_consulta) = ?;"
            ;
            
            // Hora Inicio y Fin de las Consultas 
            LocalTime horaInicio = LocalTime.of(15, 0);
            LocalTime horaFin = LocalTime.of(19, 0);
            
            // Generar las Horas Disponibles (15:00 a 19:00 hrs) con Intervalos de 1 Hora 
            while (!horaInicio.isAfter(horaFin)) {
                horasDisponibles.add(horaInicio.toString());
                horaInicio = horaInicio.plusMinutes(60);
            }
            
            try (PreparedStatement horasDisponiblesStmt = st.getConnection().prepareStatement(horasDisponiblesQuery)) {
                horasDisponiblesStmt.setInt(1, id_medico);
                horasDisponiblesStmt.setDate(2, Date.valueOf(fecha_cita));
                try (ResultSet rs = horasDisponiblesStmt.executeQuery()) {
                    while (rs.next()) {
                        String horaString = rs.getTimestamp("fecha_consulta").toLocalDateTime().toLocalTime().toString();
                        horasOcupadas.add(horaString);
                    }
                    // Remover Todas las Horas Ocupadas en la Lista de Horas Disponibles 
                    horasDisponibles.removeAll(horasOcupadas);
                }
            }
            return horasDisponibles;
        });
    }
    
    // Get Consultas By ID or All (Read)
    public Consulta getById(Integer id_cita) {
        return (Consulta) inStatementQuery((st) -> {
            String consultaByIdQuery = "SELECT * FROM consulta WHERE id_consulta = ?;";
            try (PreparedStatement consultaByIdStmt = st.getConnection().prepareStatement(consultaByIdQuery)) {
                consultaByIdStmt.setInt(1, id_cita);
                try (ResultSet rs = consultaByIdStmt.executeQuery()) {
                    if (rs.next()) {
                        var medico = EntityDAOPool.instance().getMedicoDAO().getById(rs.getInt("id_medico"));
                        var paciente = EntityDAOPool.instance().getPacienteDAO().getById(rs.getInt("id_paciente"));
                        var servicio = EntityDAOPool.instance().getServicioDAO().getTypeService(rs.getInt("id_servicio"));
                        return new Consulta(
                            rs.getInt("id_consulta"),
                            medico,
                            paciente,
                            servicio,
                            rs.getTimestamp("fecha_consulta").toLocalDateTime()
                        );
                    } else {
                        return null;
                    }
                }
            }
        });
    }
    
    public ArrayList<Consulta> getAllConsultas(Integer id_medico) {
        return (ArrayList<Consulta>) inStatementQuery((st) -> {
            var consultas = new ArrayList<Consulta>();
            String consultaUsuarioQuery = 
                "SELECT " + 
                "consulta.id_consulta, " + 
                "consulta.id_medico, " + 
                "consulta.id_paciente, " +
                "consulta.id_servicio, " + 
                "consulta.fecha_consulta, " + 
                "medicos.id_medico, " +
                "medicos.id_usuario, " +
                "pacientes.id_paciente, " +
                "pacientes.id_usuario " +
                "FROM consulta " + 
                "INNER JOIN medicos ON consulta.id_medico = medicos.id_medico " + 
                "INNER JOIN pacientes ON consulta.id_paciente = pacientes.id_paciente " + 
 "WHERE medicos.id_medico = ?;";
            try (PreparedStatement consultasUsuarioStmt = st.getConnection().prepareStatement(consultaUsuarioQuery)) {
                consultasUsuarioStmt.setInt(1, id_medico);
                try (var consultasUsuario = consultasUsuarioStmt.executeQuery()) {
                    while (consultasUsuario.next()) {
                        var medico = EntityDAOPool.instance().getMedicoDAO().getById(consultasUsuario.getInt("id_medico"));
                        var paciente = EntityDAOPool.instance().getPacienteDAO().getById(consultasUsuario.getInt("id_paciente"));
                        var servicio = EntityDAOPool.instance().getServicioDAO().getTypeService(consultasUsuario.getInt("id_servicio"));
                        
                        Consulta consulta = new Consulta(
                            consultasUsuario.getInt("id_consulta"),
                            medico,
                            paciente,
                            servicio,
                            consultasUsuario.getTimestamp("fecha_consulta").toLocalDateTime()
                        );
                        
                        consultas.add(consulta);
                    }
                    return consultas;
                }
            }
        });
    }

    public ArrayList<Consulta> getAllConsultasPaciente(Integer id_paciente) {
        return (ArrayList<Consulta>) inStatementQuery((st) -> {
            var consultas = new ArrayList<Consulta>();
            String consultaUsuarioQuery
                    = "SELECT * FROM consulta "
                    + "INNER JOIN medicos ON medicos.id_medico = consulta.id_medico "
                    + "INNER JOIN pacientes ON pacientes.id_paciente = consulta.id_paciente "
                    + "WHERE consulta.id_paciente = ?;";
            try (PreparedStatement consultasUsuarioStmt = st.getConnection().prepareStatement(consultaUsuarioQuery)) {
                consultasUsuarioStmt.setInt(1, id_paciente);
                try (var consultasUsuario = consultasUsuarioStmt.executeQuery()) {
                    while (consultasUsuario.next()) {
                        var medico = EntityDAOPool.instance().getMedicoDAO().getById(consultasUsuario.getInt("id_medico"));
                        var paciente = EntityDAOPool.instance().getPacienteDAO().getById(consultasUsuario.getInt("id_paciente"));
                        var servicio = EntityDAOPool.instance().getServicioDAO().getTypeService(consultasUsuario.getInt("id_servicio"));

                        Consulta consulta = new Consulta(
                                consultasUsuario.getInt("id_consulta"),
                                medico,
                                paciente,
                                servicio,
                                consultasUsuario.getTimestamp("fecha_consulta").toLocalDateTime()
                        );

                        consultas.add(consulta);
                    }
                    return consultas;
                }
            }
        });
    }
    
    // Update Consulta With Object 
    public int updateConsulta(Consulta consulta) {
        return inStatementUpdate((st) -> {
            String updateConsultaQuery = "UPDATE consulta SET id_medico = ?, id_paciente = ?, id_servicio = ?, fecha_consulta = ? WHERE id_consulta = ?;";
            try (PreparedStatement updateConsultaStmt = st.getConnection().prepareStatement(updateConsultaQuery)) {
                updateConsultaStmt.setInt(1, consulta.medico().id_medico());
                updateConsultaStmt.setInt(2, consulta.paciente().id_paciente());
                updateConsultaStmt.setInt(3, consulta.servicio().id_servicio());
                updateConsultaStmt.setTimestamp(4, Timestamp.valueOf(consulta.fecha_consulta()));
                updateConsultaStmt.setInt(5, consulta.id_consulta());
                
                int affectedRows = updateConsultaStmt.executeUpdate();
                
                if (affectedRows > 0) {
                    return affectedRows; // Numero de Filas Afectadas (Se Actualizo la Consulta)
                } else {
                    return -1; // No Se Actualizo Ninguna Fila
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return -1;
            }
        });
    }
    
    // Delete Consulta By ID
    public int deleteById(Integer id_cita) {
        return inStatementUpdate((st) -> {
           String deleteConsultaQuery = "DELETE FROM consulta WHERE id_consulta = ?;"; 
           try (PreparedStatement deleteConsultaStmt = st.getConnection().prepareStatement(deleteConsultaQuery)) {
               deleteConsultaStmt.setInt(1, id_cita);
               
               int affectedRows = deleteConsultaStmt.executeUpdate();
               
               if (affectedRows > 0) {
                   return affectedRows; // Se Borro El Usuario
               } else {
                   return -1; // No Se Elimino Ninguna Fila
               }
           } catch (SQLException e) {
               e.printStackTrace();
               return -1; // Error SQL
           }
        });
    }
    
}
