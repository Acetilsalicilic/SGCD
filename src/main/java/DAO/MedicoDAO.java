/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Records.Especialidad;
import Records.Medico;
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
public class MedicoDAO extends AbstractEntityDAO {
    
    public MedicoDAO(Connection con) {
        super(con);
    }
    
    // Create Medico Method
    public int createMedico(Medico medico) {
      return inStatementUpdate((st) -> {
          String createMedicoQuery = "INSERT INTO medicos (id_usuario, id_especialidad, nombre, apellidos) VALUES (?, ?, ?, ?);";
          try (PreparedStatement createMedicoStmt = st.getConnection().prepareStatement(createMedicoQuery, Statement.RETURN_GENERATED_KEYS)) {
              createMedicoStmt.setInt(1, medico.usuario().id_usuario());
              createMedicoStmt.setInt(2, medico.especialidad().id_especialidad());
              createMedicoStmt.setString(3, medico.nombre_medico());
              createMedicoStmt.setString(4, medico.apellidos_medico());
              
              int affectedRows = createMedicoStmt.executeUpdate();
              
              if (affectedRows == 0) {
                  throw new SQLException("Error al Crear al Medico. No se Creo el Registro.");
              }
              
              try (ResultSet generatedKeys = createMedicoStmt.getGeneratedKeys()) {
                  if (generatedKeys.next()) {
                      return generatedKeys.getInt(1);
                  } else {
                      throw new SQLException("Error al Crear al Medico. No se Obtuvo el ID Generado.");
                  }
              }
          }
      });
    }
    
    // Get Medico By ID or All (Read)
    public Medico getById(Integer id_medico) {
        return (Medico) inStatementQuery((st) -> {

            String medicoByIdQuery = "SELECT * FROM medicos WHERE id_medico = ?;";

            try (PreparedStatement medicoByIdStmt = st.getConnection().prepareStatement(medicoByIdQuery)) {
                medicoByIdStmt.setInt(1, id_medico);
                try (var medicoById = medicoByIdStmt.executeQuery()) {
                    if (medicoById.next()) {
                        var usuario = EntityDAOPool.instance().getUsuarioDAO().getById(medicoById.getInt("id_usuario"));
                        var especialidad = EntityDAOPool.instance().getEspecialidadDAO().getTypeEspecialidad(medicoById.getInt("id_especialidad"));
                        return new Medico (
                                medicoById.getInt("id_medico"),
                                usuario,
                                especialidad,
                                medicoById.getString("nombre"),
                                medicoById.getString("apellidos")
                        );
                    } else {
                        return null;
                    }
                }
            }
        });
    }
    
    public ArrayList<Medico> getAll() {
        return (ArrayList<Medico>) inStatementQuery((st) -> {
            var medicos = new ArrayList<Medico>();
            String medicosAllQuery = "SELECT * FROM medicos;";
            try (PreparedStatement medicosAllStmt = st.getConnection().prepareStatement(medicosAllQuery)) {
                try (var medicosAll = medicosAllStmt.executeQuery()) {
                    while (medicosAll.next()) {
                        var usuario = EntityDAOPool.instance().getUsuarioDAO().getById(medicosAll.getInt("id_usuario"));
                        var especialidad = EntityDAOPool.instance().getEspecialidadDAO().getTypeEspecialidad(medicosAll.getInt("id_especialidad"));
                        medicos.add(
                            new Medico (
                                medicosAll.getInt("id_medico"),
                                usuario,
                                especialidad,
                                medicosAll.getString("nombre"),
                                medicosAll.getString("apellidos")
                            )
                       );
                    }
                    return medicos;
                }
            }
        });
    }

    // Find Medico By Name
    public ArrayList<Medico> getByNombre(String nombre_medico) {
        return (ArrayList<Medico>) inStatementQuery((st) -> {
           ArrayList<Medico> medicos = new ArrayList<>(); 
           String getByNameQuery = "SELECT * FROM medicos WHERE nombre LIKE ?;";
           try (PreparedStatement getByNameStmt = st.getConnection().prepareStatement(getByNameQuery)) {
               if (nombre_medico != null) {
                   getByNameStmt.setString(1, "%" + nombre_medico + "%");
               }
               try (ResultSet rs = getByNameStmt.executeQuery()) {
                    while (rs.next()) {
                    var usuario = EntityDAOPool.instance().getUsuarioDAO().getById(rs.getInt("id_usuario"));
                    var especialidad = EntityDAOPool.instance().getEspecialidadDAO().getTypeEspecialidad(rs.getInt("id_especialidad"));
                    Medico medico = new Medico (
                        rs.getInt("id_medico"),
                        usuario,
                        especialidad,
                        rs.getString("nombre"),
                        rs.getString("apellidos")
                    );
                    medicos.add(medico);
                    }
                }
            }
           return medicos;
        });
    }
    
    // Find Medico By Speciality
    public ArrayList<Medico> getBySpeciality(String desc_espe) {
        return (ArrayList<Medico>) inStatementQuery((st) -> {
            ArrayList<Medico> medicos = new ArrayList<>();
            Especialidad especialidadFind = EntityDAOPool.instance().getEspecialidadDAO().getByDescription(desc_espe);
            if (especialidadFind != null) {
                String getMedicosBySpecialityQuery = "SELECT * FROM medicos WHERE id_especialidad = ?;";
                try (PreparedStatement getMedicosBySpecialityStmt = st.getConnection().prepareStatement(getMedicosBySpecialityQuery)) {
                    getMedicosBySpecialityStmt.setInt(1, especialidadFind.id_especialidad());
                    try (ResultSet rs = getMedicosBySpecialityStmt.executeQuery()) {
                        while (rs.next()) {
                            var usuario = EntityDAOPool.instance().getUsuarioDAO().getById(rs.getInt("id_usuario"));
                            Medico medico = new Medico(
                                rs.getInt("id_medico"),
                                usuario,
                                especialidadFind,
                                rs.getString("nombre"),
                                rs.getString("apellidos")
                            );
                            medicos.add(medico);
                        }
                    }
                }
            }
            return medicos;
        });
    }
    
    // Update Medico With Object
    public int updateMedico(Medico medico) {
        return inStatementUpdate((st) -> {
            String updateMedicoQuery = "UPDATE medicos SET id_usuario = ?, id_especialidad = ?, nombre = ?, apellidos = ? WHERE id_medico = ?;";
            try (PreparedStatement updateMedicoStmt = st.getConnection().prepareStatement(updateMedicoQuery)) {
                updateMedicoStmt.setInt(1, medico.usuario().id_usuario());
                updateMedicoStmt.setInt(2, medico.especialidad().id_especialidad());
                updateMedicoStmt.setString(3, medico.nombre_medico());
                updateMedicoStmt.setString(4, medico.apellidos_medico());
                updateMedicoStmt.setInt(5, medico.id_medico());                
                int affectedRows = updateMedicoStmt.executeUpdate();
                
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
    
    // Delete Medico By ID
    public int deleteMedicoById(Integer id_medico) {
        return inStatementUpdate((st) -> {
            String deleteMedicoQuery = "DELETE FROM medicos WHERE id_medico = ?;";
            try (PreparedStatement deleteMedicoStmt = st.getConnection().prepareStatement(deleteMedicoQuery)) {
                deleteMedicoStmt.setInt(1, id_medico);
                
                int affectedRows = deleteMedicoStmt.executeUpdate();
                
                if (affectedRows > 0){
                    return affectedRows; // Se Borro el Medico
                } else  {
                    return -1; // No se Elimino Ninguna Fila
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return -1; // Error SQL
            }
        });
    }

//    public int delete(int id) {
//        return preparedUpdate((con) -> {
//            var st = con.prepareStatement("DELETE FROM medicos WHERE id_medico=?;");
//            st.setInt(1, id);
//
//            return 1;
//        });
//    }
}
