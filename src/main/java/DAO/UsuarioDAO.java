/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Records.Usuario;
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
public class UsuarioDAO extends AbstractEntityDAO {
    public UsuarioDAO(Connection con) {
        super(con);
    }
    
    // Create Usuario
    public int create(Usuario usuario) {
        return inStatementUpdate((st) -> {
            String createUsuarioQuery = "INSERT INTO usuarios (id_tipo_usuario, nombre_usuario, contrasena) VALUES (?, ?, ?);";
            try (PreparedStatement createUsuarioStmt = st.getConnection().prepareStatement(createUsuarioQuery, Statement.RETURN_GENERATED_KEYS)) {
                createUsuarioStmt.setInt(1, usuario.id_tipo_usuario());
                createUsuarioStmt.setString(2, usuario.nombre_usuario());
                createUsuarioStmt.setString(3, usuario.contrasena());
                
                // Se Ejecuta el Update y se Obtiene el Numero de Filas Afectadas
                int affectedRows = createUsuarioStmt.executeUpdate();
                
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = createUsuarioStmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            return generatedKeys.getInt(1); // Return ID User Created
                        } else {
                            System.err.println("No Se Genero Ninguna Clave");
                            return -1; // No Se Genero Ninguna Clave
                        }
                    } catch (SQLException e) {
                        System.err.println("Errror Al Obtener Claves Generadas: " + e.getLocalizedMessage());
                        e.printStackTrace();
                        return -1; // SQL Error
                    }
                } else {
                    return -1; // No Se Afectaron Filas
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return 0; // Error SQL 
            }
        });
    }
    
    // Get Usuario By ID or All (Read)
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
    
    public int update(Usuario usuario) {
        return inStatementUpdate((st) -> {
            String updateUsuarioQuery = "UPDATE usuarios SET id_tipo_usuario = ?, nombre_usuario = ?, contrasena = ? WHERE id_usuario = ?;";
            try (PreparedStatement updateUsuarioStmt = st.getConnection().prepareStatement(updateUsuarioQuery)) {
                updateUsuarioStmt.setInt(1, usuario.id_tipo_usuario());
                updateUsuarioStmt.setString(2, usuario.nombre_usuario());
                updateUsuarioStmt.setString(3, usuario.contrasena());
                updateUsuarioStmt.setInt(4, usuario.id_usuario());
                
                // Numero de Filas Afectadas con el Update
                int affectedRows = updateUsuarioStmt.executeUpdate();
                
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
    
    public int deleteById(Integer id_usuario) {
        return inStatementUpdate((st) -> {
            String deleteUsuarioQuery = "DELETE FROM usuarios WHERE id_usuario = ?";
            
            try (PreparedStatement deleteUsuariosStmt = st.getConnection().prepareStatement(deleteUsuarioQuery)) {
                deleteUsuariosStmt.setInt(1, id_usuario);
                
                int affectedRows = deleteUsuariosStmt.executeUpdate();
                
                if (affectedRows > 0) {
                    return 1; // Se Borro el Usuario
                } else {
                    return -1; // No Se Elimino Ninguna Fila
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return -1; // Error SQL
            }
        });
    }

    public Usuario getByUsername(String username) {
        return (Usuario) inStatementQuery((st) -> {
            var rs = st.executeQuery("SELECT * FROM usuarios WHERE nombre_usuario = '" + username + "';");
            rs.next();

            var tipoUsuario = EntityDAOPool.instance().getTipoUsuarioDAO().getTypeUser(rs.getInt("id_usuario"));

            return new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getInt("id_tipo_usuario"),
                    rs.getString("nombre_usuario"),
                    rs.getString("contrasena"),
                    tipoUsuario.desc_tipo()
            );
        });
    }
}
