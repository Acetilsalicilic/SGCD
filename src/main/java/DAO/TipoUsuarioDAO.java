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
    
    public TipoUsuario getTypeUser(Integer id_tipo_usuario) {
        return (TipoUsuario) inStatementQuery((st) -> {
            String tipoUsuarioQuery = "SELECT * FROM tipo_usuario WHERE id_tipo = ?;";
            try (PreparedStatement tipoUsuarioStmt = st.getConnection().prepareStatement(tipoUsuarioQuery)) {
                tipoUsuarioStmt.setInt(1, id_tipo_usuario);
                try (var tipoUsuario = tipoUsuarioStmt.executeQuery()) {
                    if (tipoUsuario.next()) {
                        return new TipoUsuario(
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

    public TipoUsuario getByDesc(String desc) {
        return (TipoUsuario) inStatementQuery((st) -> {
            var rs = st.executeQuery("SELECT * FROM tipo_usuario WHERE desc_tipo='" + desc + "';");
            rs.next();

            return new TipoUsuario(
                    rs.getInt("id_tipo"),
                    rs.getString("desc_tipo")
            );
        });
    }
}
