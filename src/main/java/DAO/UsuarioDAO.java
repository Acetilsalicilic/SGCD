/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Records.Usuario;
import java.sql.Connection;
import java.util.ArrayList;

/**
 *
 * @author W10
 */
public class UsuarioDAO extends AbstractEntityDAO {
    public UsuarioDAO(Connection con) {
        super(con);
    }
    
    public ArrayList<Usuario> getAll() {
        return (ArrayList<Usuario>) inStatementQuery((st) -> {
            var usuarios = new ArrayList<Usuario>();
            
            var rs = st.executeQuery("select\n" +
                "	usuarios.nombre_usuario as usuario,\n" +
                "	usuarios.id_usuario as id,\n" +
                "	usuarios.contrasena as contrasena,\n" +
                "	tipo_usuario.desc_tipo as tipo\n" +
                "from\n" +
                "	usuarios\n" +
                "inner join\n" +
                "	tipo_usuario\n" +
                "	on tipo_usuario.id_tipo = usuarios.id_tipo_usuario;");
            
            while (rs.next()) {
                usuarios.add(new Usuario(
                        rs.getInt("id"),
                        rs.getString("usuario"),
                        rs.getString("contrasena"),
                        rs.getString("tipo")
                ));
            }
            
            return usuarios;
        });
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
"	usuarios.id_usuario = " + id + ";");
            
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
    
    public int update(Usuario usuario) {
        return inStatementUpdate((st) -> {
            var rs = st.executeQuery("select id_tipo from tipo_usuario where desc_tipo = '" + usuario.tipo() + "';");
            rs.next();
            var id_tipo = rs.getInt("id_tipo");
            return st.executeUpdate("UPDATE usuarios SET " + 
                    "id_usuario=" + usuario.id() + 
                    ", id_tipo_usuario='" + id_tipo +
                    "', nombre_usuario='" + usuario.nombre_usuario() +
                    "', contrasena='" + usuario.contrasena() +
                    "' WHERE id_usuario=" + usuario.id() + ";");
        });
    }
    
    public int create(Usuario usuario) {
        return inStatementUpdate((st) -> {
            var rs = st.executeQuery("SELECT id_tipo FROM tipo_usuario WHERE desc_tipo='" + usuario.tipo() + "';");
            rs.next();
            var id_tipo = rs.getInt("id_tipo");
            return st.executeUpdate("INSERT INTO usuarios VALUES (" +
                    usuario.id() + ", " +
                    id_tipo + ", " +
                    "'" + usuario.nombre_usuario() + "', " +
                    "'" + usuario.contrasena() + "');");
        });
    }
}
