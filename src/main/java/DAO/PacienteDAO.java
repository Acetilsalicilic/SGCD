/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Records.Paciente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 *
 * @author W10
 */
public class PacienteDAO extends AbstractEntityDAO {
    
    public PacienteDAO(Connection con) {
        super(con);
    }
    
    public Paciente getById(Integer id_paciente){
        return (Paciente) inStatementQuery((st) -> {
            String pacienteByIdQuery = "SELECT * FROM pacientes WHERE id_paciente = ?;";
            try (PreparedStatement pacienteByIdStmt = st.getConnection().prepareStatement(pacienteByIdQuery)) {
                pacienteByIdStmt.setInt(1, id_paciente);
                
                try (var pacienteById = pacienteByIdStmt.executeQuery()) {
                    if (pacienteById.next()) {
                        var usuario = EntityDAOPool.instance().getUsuarioDAO().getById(pacienteById.getInt("id_usuario"));
                        return new Paciente (
                            pacienteById.getInt("id_paciente"),
                            usuario,
                            pacienteById.getString("nombre"),
                            pacienteById.getString("apellidos"),
                            pacienteById.getString("telefono"),
                            pacienteById.getString("direccion")
                        );
                    } else {
                        return null;
                    }
                }
            }
        });
    }
    
    public ArrayList<Paciente> getAll() {
        return (ArrayList<Paciente>) inStatementQuery((st) -> {
            var pacientes = new ArrayList<Paciente>();
            String pacientesAllQuery = "SELECT * FROM pacientes";
            try (PreparedStatement pacientesAllStmt = st.getConnection().prepareStatement(pacientesAllQuery)) {
                try (var pacientesAll = pacientesAllStmt.executeQuery()) {
                    while (pacientesAll.next()) {
                        var usuario = EntityDAOPool.instance().getUsuarioDAO().getById(pacientesAll.getInt("id_usuario"));
                        pacientes.add(
                            new Paciente (
                                pacientesAll.getInt("id_paciente"),
                                usuario, 
                                pacientesAll.getString("nombre"),
                                pacientesAll.getString("apellidos"),
                                pacientesAll.getString("telefono"),
                                pacientesAll.getString("direccion")
                            )
                        );
                    }
                    return pacientes;
                }
            }
        });
    }
    
//    public ArrayList<Paciente> getAll() {
//        return (ArrayList<Paciente>) inStatementQuery((st) -> {
//            var pacientes = new ArrayList<Paciente>();
//            
//            var rs = st.executeQuery("SELECT * FROM pacientes");
//            
//            while(rs.next()) {
//                
//                var usuario = EntityDAOPool.instance().getUsuarioDAO().getById(rs.getInt("id_usuario"));
//                
//                pacientes.add(new Paciente(
//                        rs.getInt("id_paciente"),
//                        rs.getString("nombre"),
//                        rs.getString("apellidos"),
//                        rs.getString("telefono"),
//                        rs.getString("direccion"),
//                        usuario
//                ));
//            }
//            
//            return pacientes;
//        });
//    }
    
    
//    public ArrayList<Paciente> getByNombre(String nombre) {
//        var pacientes = getAll();
//            
//        var filteredPacientes = pacientes.stream().filter(
//                    paciente -> paciente.nombre().toLowerCase().contains(nombre.toLowerCase())
//            );
//        
//        return filteredPacientes.collect(Collectors.toCollection(ArrayList::new));
//    }
//    
//    public int create(Paciente paciente) {
//        return preparedUpdate((con) -> {
//            var st = con.prepareStatement("INSERT INTO pacientes VALUES (?, ?, ?, ?, ?, ?);");
//            
//            st.setInt(1, paciente.id_paciente());
//            st.setInt(2, paciente.usuario().id_usuario());
//            st.setString(3, paciente.nombre());
//            st.setString(4, paciente.apellidos());
//            st.setString(5, paciente.telefono());
//            st.setString(6, paciente.direccion());
//            
//            st.execute();
//            
//            return 1;
//        });
//    }
//    
//    public int deleteById(int id) {
//        return inStatementUpdate((st) -> {
//            return st.executeUpdate("DELETE FROM pacientes WHERE id_paciente='" + id + "';");
//        });
//    }
//    
//    public int update(Paciente paciente) {
//        return preparedUpdate((con) -> {
//            var st = con.prepareStatement("UPDATE pacientes SET id_paciente=?, id_usuario=?, nombre=?, apellidos=?, telefono=?, direccion=? WHERE id_paciente=?;");
//
//            st.setInt(1, paciente.id_paciente());
//            st.setInt(2, paciente.usuario().id_usuario());
//            st.setString(3, paciente.nombre());
//            st.setString(4, paciente.apellidos());
//            st.setString(5, paciente.telefono());
//            st.setString(6, paciente.direccion());
//            
//            st.execute();
//            
//            return 1;
//        });
//    }
}
