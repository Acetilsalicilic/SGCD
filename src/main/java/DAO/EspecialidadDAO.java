/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Records.Especialidad;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author Vega
 */
public class EspecialidadDAO extends AbstractEntityDAO {
    
    public EspecialidadDAO(Connection con) {
        super(con);
    }
    
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
    
}
