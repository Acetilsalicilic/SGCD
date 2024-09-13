/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Records.TipoUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author Vega
 */
public class TipoUsuarioDAO extends AbstractEntityDAO {
    
    public TipoUsuarioDAO(Connection con) {
        super(con);
    }
    
    public TipoUsuario getTypeUser(Integer id_usuario) {
        return (TipoUsuario) inStatementQuery((st) -> {
            String tipoUsuarioQuery = 
                "SELECT " + 
                "usuarios.id_usuario, " +
                "usuarios.id_tipo_usuario, " + 
                "tipo_usuario.id_tipo, " +
                "tipo_usuario.desc_tipo " +
                "FROM usuarios " + 
                "INNER JOIN tipo_usuario ON usuarios.id_tipo_usuario = tipo_usuario.id_tipo " +
                "WHERE id_usuario = ?;"
            ;
            try (PreparedStatement tipoUsuarioStmt = st.getConnection().prepareStatement(tipoUsuarioQuery)) {
                tipoUsuarioStmt.setInt(1, id_usuario);
                
                try (var tipoUsuario = tipoUsuarioStmt.executeQuery()) {
                    if (tipoUsuario.next()) {
                        return new TipoUsuario (
                            tipoUsuario.getInt("id_tipo"),
                            tipoUsuario.getString("desc_tipo")
                        );
                    } else {
                        return null;
                    }
                }
            }
        });
    }
    
}
