/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Records.Cita;
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
public class CitaDAO extends AbstractEntityDAO {
    
    public CitaDAO(Connection con) {
        super(con);
    }
    
    // Create Cita
    public int createCita(Cita cita) {
        return inStatementUpdate((st) -> {
            String createCitaQuery = "INSERT INTO citas (id_medico, id_paciente, id_servicio, fecha_cita) VALUES (?, ?, ?,?);";   
            try (PreparedStatement createCitaStmt = st.getConnection().prepareStatement(createCitaQuery, Statement.RETURN_GENERATED_KEYS)) {
                createCitaStmt.setInt(1, cita.medico().id_medico());
                createCitaStmt.setInt(2, cita.paciente().id_paciente());
                createCitaStmt.setInt(3, cita.servicio().id_servicio());
                createCitaStmt.setTimestamp(4, Timestamp.valueOf(cita.fecha_cita()));
                
                int affectedRows = createCitaStmt.executeUpdate();
                
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = createCitaStmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            return generatedKeys.getInt(1); // Retorna ID Cita Creada
                        } else {
                            return -1; // No Se Genero un ID
                        }
                    }
                } else {
                    return -1;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return -1;
            }
        });
    }
    
    // Methods to Check Availability of Schedule
    public List<String> horasDisponiblesCitas(Integer id_medico, LocalDate fecha_cita) {
        return (List<String>) inStatementQuery((st) -> {
            List<String> horasOcupadas = new ArrayList<>(); // Lista de Horas Ocupadas (Se Llena con el Query)
            List<String> horasDisponibles = new ArrayList<>(); // Lista de Horas Disponibles (Se Genera Automaticamente)
            String horasDisponiblesQuery = 
                "SELECT fecha_cita " +
                "FROM citas " +
                "WHERE id_medico = ? AND DATE(fecha_cita) = ?;"
            ;
            
            // Hora de Inicio y Fin de las Citas
            LocalTime horaInicio = LocalTime.of(10, 0);
            LocalTime horaFin = LocalTime.of(13, 0);
            
            //  Generar las Horas Disponibles (10:00 a 13:00 hrs) con Intervalos de 30 Minutos
            while (!horaInicio.isAfter(horaFin)) {
                horasDisponibles.add(horaInicio.toString());
                horaInicio = horaInicio.plusMinutes(30);
            }
            
            try (PreparedStatement horasDisponiblesStmt = st.getConnection().prepareStatement(horasDisponiblesQuery)) {
                horasDisponiblesStmt.setInt(1, id_medico);
                horasDisponiblesStmt.setDate(2, Date.valueOf(fecha_cita));
                
                try (ResultSet rs = horasDisponiblesStmt.executeQuery()) {
                    while (rs.next()) {
                        String horaString = rs.getTimestamp("fecha_cita").toLocalDateTime().toLocalTime().toString();
                        horasOcupadas.add(horaString);
                    }
                    // Remover Todas las Horas Ocupadas en la lista de horas disponibles
                    horasDisponibles.removeAll(horasOcupadas);
                }
            }
            return horasDisponibles;
        });
    }
    
    // Get Citas By ID or All (Read)
    public Cita getById(Integer id_cita) {
        return (Cita) inStatementQuery((st) -> {
            String citaByIdQuery = "SELECT * FROM citas WHERE id_cita = ?;";
            try (PreparedStatement citaByIdStmt = st.getConnection().prepareStatement(citaByIdQuery)) {
                citaByIdStmt.setInt(1, id_cita);
                try (ResultSet rs = citaByIdStmt.executeQuery()) {
                    if (rs.next()) {
                        var medico = EntityDAOPool.instance().getMedicoDAO().getById(rs.getInt("id_medico"));
                        var paciente = EntityDAOPool.instance().getPacienteDAO().getById(rs.getInt("id_paciente"));
                        var servicio = EntityDAOPool.instance().getServicioDAO().getTypeService(rs.getInt("id_servicio"));
                        return new Cita(
                            rs.getInt("id_cita"),
                            medico,
                            paciente,
                            servicio,
                            rs.getTimestamp("fecha_cita").toLocalDateTime()
                        );
                    } else {
                        return null;
                    }
                }
            }
        });
    }
    
    public ArrayList<Cita> getAllCitas (Integer id_usuario) {
        return (ArrayList<Cita>) inStatementQuery((st) -> {
            var citas = new ArrayList<Cita>();
            String citasUsuarioQuery = 
                "SELECT " + 
                "citas.id_cita, " + 
                "citas.id_medico, " + 
                "citas.id_paciente, " +
                "citas.id_servicio, " + 
                "citas.fecha_cita, " + 
                "medicos.id_medico, " +
                "medicos.id_usuario, " +
                "pacientes.id_paciente, " +
                "pacientes.id_usuario " +
                "FROM citas " + 
                "INNER JOIN medicos ON citas.id_medico = medicos.id_medico " + 
                "INNER JOIN pacientes ON citas.id_paciente = pacientes.id_paciente " + 
                "WHERE medicos.id_usuario = ? OR pacientes.id_usuario = ?;"    
            ;
            try (PreparedStatement citasUsuarioStmt = st.getConnection().prepareStatement(citasUsuarioQuery)) {
                citasUsuarioStmt.setInt(1, id_usuario);
                citasUsuarioStmt.setInt(2, id_usuario);
                
                try (var citasUsuario = citasUsuarioStmt.executeQuery()) {
                    while (citasUsuario.next()) {
                        var medico = EntityDAOPool.instance().getMedicoDAO().getById(citasUsuario.getInt("id_medico"));
                        var paciente = EntityDAOPool.instance().getPacienteDAO().getById(citasUsuario.getInt("id_paciente"));
                        var servicio = EntityDAOPool.instance().getServicioDAO().getTypeService(citasUsuario.getInt("id_servicio"));
                        
                        Cita cita = new Cita (
                            citasUsuario.getInt("id_cita"),
                            medico,
                            paciente,
                            servicio,
                            citasUsuario.getTimestamp("fecha_cita").toLocalDateTime()
                        );

                        citas.add(cita);
                    }
                    return citas;
                }
            }
        });
    }
    
    // Update Cita With Object
    public int updateCita(Cita cita) {
        return inStatementUpdate((st) -> {
            String updateCitaQuery = "UPDATE citas SET id_medico = ?, id_paciente = ?, id_servicio = ?, fecha_cita = ?  WHERE id_cita = ?;";
            try (PreparedStatement updateCitaStmt = st.getConnection().prepareStatement(updateCitaQuery)) {
                updateCitaStmt.setInt(1, cita.medico().id_medico());
                updateCitaStmt.setInt(2, cita.paciente().id_paciente());
                updateCitaStmt.setInt(3, cita.servicio().id_servicio());
                updateCitaStmt.setTimestamp(4, Timestamp.valueOf(cita.fecha_cita()));
                updateCitaStmt.setInt(5, cita.id_cita());
                
                int affectedRows = updateCitaStmt.executeUpdate();
                
                if (affectedRows > 0) {
                    return affectedRows; // Numero de Filas Afectadas (Se Actualizo la Cita)
                } else {
                    return -1; // No se Actualizo Ninguna Fila
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return -1;
            }
        });
    }
    
    // Delete Cita By ID
    public int deleteCitaById(Integer id_cita) {
        return inStatementUpdate((st) -> {
            String deleteCitaQuery = "DELETE FROM citas WHERE id_cita = ?;";
            try (PreparedStatement deleteCitaStmt = st.getConnection().prepareStatement(deleteCitaQuery)) {
                deleteCitaStmt.setInt(1, id_cita);
                
                int affectedRows = deleteCitaStmt.executeUpdate();
                
                if (affectedRows > 0) {
                    return affectedRows; // Se Borro el Usuario 
                } else {
                    return -1; // No se Elimino Ninguna Fila 
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return -1; // Error SQL
            }
        });
    }
    
}
