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
    
    public Usuario getById(Integer id) {
        return (Usuario) inStatementQuery((st) -> {
            
            var rs = st.executeQuery(
                "SELECT usuarios.id_usuario, usuarios.id_tipo_usuario, usuarios.nombre_usuario, usuarios.contrasena, tipo_usuario.desc_tipo " +
                "FROM usuarios " +
                "INNER JOIN tipo_usuario ON tipo_usuario.id_tipo = usuarios.id_tipo_usuario " +
                "WHERE usuarios.id_usuario = " + id + ";"
            );
            
            rs.next();
            
            return new Usuario(
                rs.getInt("id_usuario"),
                rs.getInt("id_tipo_usuario"),
                rs.getString("nombre_usuario"),
                rs.getString("contrasena"),
                rs.getString("desc_tipo")
            );
        });
    }
    
    public ArrayList<Usuario> getAll() {
        return (ArrayList<Usuario>) inStatementQuery((st) -> {
            
            var usuarios = new ArrayList<Usuario>();
            
            var rs = st.executeQuery(
                "SELECT usuarios.id_usuario, usuarios.id_tipo_usuario, usuarios.nombre_usuario, usuarios.contrasena, tipo_usuario.desc_tipo " +
                "FROM usuarios " +
                "INNER JOIN tipo_usuario ON tipo_usuario.id_tipo = usuarios.id_tipo_usuario;"     
            );
            
            while(rs.next()){
                usuarios.add(new Usuario (
                    rs.getInt("id_usuario"),
                    rs.getInt("id_tipo_usuario"),
                    rs.getString("nombre_usuario"),
                    rs.getString("contrasena"),
                    rs.getString("desc_tipo")
                ));
            }
            
            return usuarios;
            
        });
    }
    
    public int deleteById(Integer id) {
        return inStatementUpdate((st) -> {
            return st.executeUpdate("DELETE FROM usuarios WHERE id_usuario=" + id + ";");
        });
    }
    
    public int update(Usuario usuario) {
        return inStatementUpdate((st)-> {
           return st.executeUpdate(
                "UPDATE usuarios SET " + 
                "id_tipo_usuario=" + usuario.id_tipo_usuario() +
                ", nombre_usuario='" + usuario.nombre_usuario() +
                "', contrasena='" + usuario.contrasena() +
                "' WHERE id_usuario=" + usuario.id_usuario() + ";"
            );
        });
    }
        
//    public int create(Usuario usuario) {
//        return inStatementUpdate((st) -> {
//            var rs = st.executeQuery("SELECT id_tipo FROM tipo_usuario WHERE desc_tipo='" + usuario.tipo() + "';");
//            rs.next();
//            var id_tipo = rs.getInt("id_tipo");
//            return st.executeUpdate("INSERT INTO usuarios VALUES (" +
//                    usuario.id_usuario() + ", " +
//                    id_tipo + ", " +
//                    "'" + usuario.nombre_usuario() + "', " +
//                    "'" + usuario.contrasena() + "');");
//        });
//    }
}
