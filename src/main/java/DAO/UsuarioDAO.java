/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Records.Usuario;
import java.sql.Connection;

/**
 *
 * @author W10
 */
public class UsuarioDAO extends AbstractEntityDAO {
    public UsuarioDAO(Connection con) {
        super(con);
    }
    
    public Usuario getById(Integer id) {
        return (Usuario) inStatementQuery((st) -> {
            var rs = st.executeQuery("select\n" +
                "	usuarios.nombre_usuario as usuario,\n" +
                "	usuarios.id_usuario as id,\n" +
                "	usuarios.contrasena,\n" +
                "	tipo_usuario.desc_tipo as tipo\n" +
                "from\n" +
                "	usuarios\n" +
                "inner join\n" +
                "	tipo_usuario\n" +
                "	on tipo_usuario.id_tipo = usuarios.id_tipo_usuario \n" +
                "where\n" +
"	usuarios.id_usuario = 1;");
            
            rs.next();
            return new Usuario(
                    rs.getInt("id"),
                    rs.getString("usuario"),
                    rs.getString("contrasena"),
                    rs.getString("tipo")
            );
        });
    }
    
    public int deleteById(Integer id) {
        return inStatementUpdate((st) -> {
            return st.executeUpdate("DELETE FROM usuarios WHERE id=" + id + ";");
        });
    }
}
