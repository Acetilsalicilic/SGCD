/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Records.Cita;
import Records.Especialidad;
import Records.Medico;
import Records.Paciente;
import Records.Servicio;
import Records.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Vega
 */
public class CitaDAO extends AbstractEntityDAO {
    
    public CitaDAO(Connection con) {
        super(con);
    }
        
    public ArrayList<Cita> getAllUserCitas (Integer id_usuario) {
        return (ArrayList<Cita>) inStatementQuery((st) -> {
            
            var tipoUsuario = st.executeQuery("SELECT id_tipo_usuario FROM usuarios WHERE id_usuario = " + id_usuario + ";");
            
            int idTipoUsuario = -1;
            if (tipoUsuario.next()){
                idTipoUsuario = tipoUsuario.getInt("id_tipo_usuario");
            }
            
            String sql = buildSqlQuery(id_usuario, idTipoUsuario);
            
            var rs = st.executeQuery(sql);
            
            ArrayList<Cita> citas = new ArrayList<>();
            
            while(rs.next()) {
                
                Usuario usuario = new Usuario(
                    id_usuario,
                    idTipoUsuario
                );
                
                Especialidad especialidad = new Especialidad (
                    rs.getInt("id_especialidad"),
                    rs.getString("desc_espe")
                );
                
                Medico medico = new Medico (
                    rs.getInt("id_medico"),
                    rs.getString("medico_nombre"),
                    rs.getString("medico_apellidos"),
                    especialidad,
                    usuario
                );
                
                Paciente paciente = new Paciente(
                    rs.getInt("id_paciente"),
                    rs.getString("paciente_nombre"),
                    rs.getString("paciente_apellidos"),
                    rs.getString("paciente_telefono"),
                    rs.getString("paciente_direccion"),
                    usuario
                );
                
                Servicio servicio = new Servicio (
                    rs.getInt("id_servicio"),
                    rs.getString("desc_servicio")
                );
                
                Cita cita = new Cita (
                    rs.getInt("id_cita"),
                    medico,
                    paciente,
                    servicio,
                    rs.getTimestamp("fecha_cita").toLocalDateTime()
                );
                
                citas.add(cita);
                
            }
            
            return citas;
            
        });
        
    }
    
    private String buildSqlQuery (Integer id_usuario, Integer id_tipo_usuario) {
        
        String sqlQuery = (id_tipo_usuario == 1) ? "medicos" : "pacientes";
        
        String sql = 
            "SELECT " + 
            "citas.id_cita, " + 
            "citas.id_medico, " + 
            "citas.id_paciente, " + 
            "citas.id_servicio, " + 
            "citas.fecha_cita, " + 
            "pacientes.id_paciente, " +
            "pacientes.id_usuario, " + 
            "pacientes.nombre AS paciente_nombre, " + 
            "pacientes.apellidos AS paciente_apellidos, " + 
            "pacientes.telefono AS paciente_telefono, " + 
            "pacientes.direccion as paciente_direccion, " + 
            "medicos.id_medico, " +
            "medicos.id_usuario, " +
            "medicos.id_especialidad, " + 
            "medicos.nombre AS medico_nombre, " + 
            "medicos.apellidos AS medico_apellidos, " + 
            "especialidades.id_especialidad, " + 
            "especialidades.desc_espe, " +
            "servicios.id_servicio, " +
            "servicios.desc_servicio " +
            "FROM citas " + 
            "INNER JOIN " + 
                "pacientes ON citas.id_paciente = pacientes.id_paciente " + 
            "INNER JOIN " + 
                "medicos ON citas.id_medico = medicos.id_medico " + 
            "INNER JOIN " + 
                "especialidades ON medicos.id_especialidad = especialidades.id_especialidad " + 
            "INNER JOIN " + 
                "servicios ON citas.id_servicio = servicios.id_servicio " + 
            "WHERE " + 
                "pacientes.id_usuario = " + id_usuario + " OR medicos.id_usuario = " + id_usuario + ";"
        ;
        
        return sql;
        
    }
    
}
