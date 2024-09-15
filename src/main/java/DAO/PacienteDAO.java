/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Records.Paciente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author W10
 */
public class PacienteDAO extends AbstractEntityDAO {
    
    public PacienteDAO(Connection con) {
        super(con);
    }
    
    // Create Paciente Method
    public int createPaciente(Paciente paciente) {
        return inStatementUpdate((st) -> {
            String createPacienteQuery = "INSERT INTO pacientes (id_usuario, nombre, apellidos, telefono, direccion) VALUES (?, ?, ?, ?, ?);";
            try (PreparedStatement createPacienteStmt = st.getConnection().prepareStatement(createPacienteQuery, Statement.RETURN_GENERATED_KEYS)) {
                createPacienteStmt.setInt(1, paciente.usuario().id_usuario());
                createPacienteStmt.setString(2, paciente.nombre());
                createPacienteStmt.setString(3, paciente.apellidos());
                createPacienteStmt.setString(4, paciente.telefono());
                createPacienteStmt.setString(5, paciente.direccion());
                
                int affectedRows = createPacienteStmt.executeUpdate();
                
                if (affectedRows == 0) {
                    throw new SQLException("Error al Crear al Paciente. No se Creo el Registro.");
                }
                
                try (ResultSet generatedKeys = createPacienteStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    } else { 
                        throw new SQLException("Error al Crear el Paciente. No se Obtuvo el ID Generado.");
                    }
                }
            }
        });
    }
    
    // Get Pacientes By ID or All (Read)
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
            String pacientesAllQuery = "SELECT * FROM pacientes;";
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

    public int create(Paciente paciente) {
        return preparedUpdate((con) -> {
            var st = con.prepareStatement("INSERT INTO pacientes (id_usuario, nombre, apellidos, telefono, direccion) VALUES (?, ?, ?, ?, ?);");

            st.setInt(1, paciente.usuario().id_usuario());
            st.setString(2, paciente.nombre());
            st.setString(3, paciente.apellidos());
            st.setString(4, paciente.telefono());
            st.setString(5, paciente.direccion());
            
            st.execute();
            
            return 1;
        });
    }

    public int create(Paciente paciente, int id_usuario) {
        return preparedUpdate((con) -> {
            var st = con.prepareStatement("INSERT INTO pacientes (id_usuario, nombre, apellidos, telefono, direccion) VALUES (?, ?, ?, ?, ?);");

            st.setInt(1, id_usuario);
            st.setString(2, paciente.nombre());
            st.setString(3, paciente.apellidos());
            st.setString(4, paciente.telefono());
            st.setString(5, paciente.direccion());

            st.execute();

            return 1;
        });
    }
    
    // Find Paciente By Name 
    public ArrayList<Paciente> getByNombre (String nombre_paciente) {
        return (ArrayList<Paciente>) inStatementQuery((st) -> {
           ArrayList<Paciente> pacientes = new ArrayList<>();
           String getByNameQuery = "SELECT * FROM pacientes WHERE nombre LIKE ?;";
           try (PreparedStatement getByNameStmt = st.getConnection().prepareStatement(getByNameQuery)) {
               if (nombre_paciente != null) {
                   getByNameStmt.setString(1, "%" + nombre_paciente + "%");
               }
               try (ResultSet rs = getByNameStmt.executeQuery()) {
                   while (rs.next()) {
                        var usuario = EntityDAOPool.instance().getUsuarioDAO().getById(rs.getInt("id_usuario"));
                        Paciente paciente = new Paciente (
                            rs.getInt("id_paciente"),
                            usuario,
                            rs.getString("nombre"),
                            rs.getString("apellidos"),
                            rs.getString("telefono"),
                            rs.getString("direccion")
                        );
                        pacientes.add(paciente);
                    }
                }
            }
            return pacientes;
        });
    }
    
    // Update Paciente With Object
    public int updatePaciente(Paciente paciente) {
        return inStatementUpdate((st) -> {
            String updatePacienteQuery = "UPDATE pacientes SET id_usuario = ?, nombre = ?, apellidos = ?, telefono = ?, direccion = ? WHERE id_paciente = ?;";
            try (PreparedStatement updatePacienteStmt = st.getConnection().prepareStatement(updatePacienteQuery)) {
                updatePacienteStmt.setInt(1, paciente.usuario().id_usuario());
                updatePacienteStmt.setString(2, paciente.nombre());
                updatePacienteStmt.setString(3, paciente.apellidos());
                updatePacienteStmt.setString(4, paciente.telefono());
                updatePacienteStmt.setString(5, paciente.direccion());
                updatePacienteStmt.setInt(6, paciente.id_paciente());
                
                // Numero de Filas Afectadas con el Update
                int affectedRows = updatePacienteStmt.executeUpdate();
                
                if (affectedRows > 0) {
                    return affectedRows; // Numero de Filas Afectadas
                } else {
                    return -1; // No se Actualizo Ninguna Fila
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return -1; // Error SQL
            }
        });
    }
    
    // Delete Paciente By ID
    public int deletePacienteById(Integer id_paciente) {
        return inStatementUpdate((st) -> {
            String deletePacienteQuery = "DELETE FROM pacientes WHERE id_paciente = ?;";
            try (PreparedStatement deletePacienteStmt = st.getConnection().prepareStatement(deletePacienteQuery)) {
                deletePacienteStmt.setInt(1, id_paciente);
                
                int affectedRows = deletePacienteStmt.executeUpdate();
                
                if (affectedRows > 0) {
                    return affectedRows; // Se Borro el Usuario
                } else {
                    return -1; // No se Eliminio Ninguna Fila
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return -1;
            }
        });
    }
}
