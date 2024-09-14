/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Records.Especialidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Vega
 */
public class EspecialidadDAO extends AbstractEntityDAO {
    
    public EspecialidadDAO(Connection con) {
        super(con);
    }
    
    
    
    // Get Especialidad By ID
    public Especialidad getTypeEspecialidad (Integer id_especialidad) {
        return (Especialidad) inStatementQuery((st) -> {
            String tipoEspecialidadQuery = "SELECT * FROM especialidades WHERE id_especialidad = ?;";
            try (PreparedStatement tipoEspecialidadStmt = st.getConnection().prepareStatement(tipoEspecialidadQuery)) {
                tipoEspecialidadStmt.setInt(1, id_especialidad);
                
                try (var tipoEspecialidad = tipoEspecialidadStmt.executeQuery()){
                    if (tipoEspecialidad.next()){
                        return new Especialidad (
                           tipoEspecialidad.getInt("id_especialidad"),
                           tipoEspecialidad.getString("desc_espe")
                        );
                    } else {
                        return null;
                    }
                }
            }
        });
    }
    
    // Get Especialidad By Description (desc_espe)
    public Especialidad getByDescription(String desc_espe) {
        return (Especialidad) inStatementQuery((st) -> {
           String getByDescriptionQuery = "SELECT * FROM especialidades WHERE desc_espe LIKE ?;"; 
           try (PreparedStatement getByDescriptionStmt = st.getConnection().prepareStatement(getByDescriptionQuery)) {
               getByDescriptionStmt.setString(1, "%" + desc_espe + "%");
               try (ResultSet rs = getByDescriptionStmt.executeQuery()) {
                   if (rs.next()) {
                       return new Especialidad(
                            rs.getInt("id_especialidad"),
                            rs.getString("desc_espe")
                       );
                   } else {
                       return null; // No Se Encontro Ninguna Especialida 
                   }
               }
           }
        });
    }
}
