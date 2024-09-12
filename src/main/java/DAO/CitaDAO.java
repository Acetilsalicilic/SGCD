/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Records.Cita;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author Vega
 */
public class CitaDAO extends AbstractEntityDAO {
    
    public CitaDAO(Connection con) {
        super(con);
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
    
}
