/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Records.TipoUsuario;
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
                TipoUsuario tipoUsuario = EntityDAOPool.instance().getTipoUsuarioDAO().getTypeUser(usuario.tipoUsuario().id_tipo());
                if (tipoUsuario == null) {
                    throw new SQLException("No se Encontro el Tipo de Usuario con el ID Proporcionado.");
                }
                createUsuarioStmt.setInt(1, tipoUsuario.id_tipo());
                createUsuarioStmt.setString(2, usuario.nombre_usuario());
                createUsuarioStmt.setString(3, usuario.contrasena());
                
                // Se Ejecuta el Update y se Obtiene el Numero de Filas Afectadas
                int affectedRows = createUsuarioStmt.executeUpdate();
                
                if (affectedRows == 0) {
                    throw new SQLException("Error al Crear el Usuario. No se Creo el Registro.");
                }
                
                try (ResultSet generatedKeys = createUsuarioStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Error al Crear el Usuario. No se Obtuvo el ID Generado.");
                    }
                }
            }
        });
    }
    
    // Get Usuario By ID or All (Read)
    public Usuario getById(Integer id_usuario) {
        return (Usuario) inStatementQuery((st) -> {
            String getUsuarioById = "SELECT * FROM usuarios WHERE id_usuario = ?;";
            try (PreparedStatement getUsuarioByIdStmt = st.getConnection().prepareStatement(getUsuarioById)) {
                getUsuarioByIdStmt.setInt(1, id_usuario);
                try (var usuarioById = getUsuarioByIdStmt.executeQuery()) {
                    if (usuarioById.next()) {
                        var tipoUsuario = EntityDAOPool.instance().getTipoUsuarioDAO().getTypeUser(usuarioById.getInt("id_tipo_usuario"));
                        return new Usuario (
                            usuarioById.getInt("id_usuario"),
                            tipoUsuario,
                            usuarioById.getString("nombre_usuario"),
                            usuarioById.getString("contrasena")
                        );
                    } else {
                        return null;
                    }
                }
            }
        });
    }
    
    public ArrayList<Usuario> getAll() {
        return (ArrayList<Usuario>) inStatementQuery((st) -> {
            var usuarios = new ArrayList<Usuario>();
            String getAllUsuariosQuery = "SELECT * FROM usuarios;";
            try (PreparedStatement getAllUsuariosStmt = st.getConnection().prepareStatement(getAllUsuariosQuery)) {
                try (var usuariosAll = getAllUsuariosStmt.executeQuery()) {
                    while (usuariosAll.next()) {
                        var tipoUsuario = EntityDAOPool.instance().getTipoUsuarioDAO().getTypeUser(usuariosAll.getInt("id_tipo_usuario"));
                        usuarios.add(
                            new Usuario (
                                usuariosAll.getInt("id_usuario"),
                                tipoUsuario,
                                usuariosAll.getString("nombre_usuario"),
                                usuariosAll.getString("contrasena")
                            )
                        );
                    }
                }
            }
            return usuarios;
        });
    }
    
    // Update Usuario With Object
    public int update(Usuario usuario) {
        return inStatementUpdate((st) -> {
            String updateUsuarioQuery = "UPDATE usuarios SET id_tipo_usuario = ?, nombre_usuario = ?, contrasena = ? WHERE id_usuario = ?;";
            try (PreparedStatement updateUsuarioStmt = st.getConnection().prepareStatement(updateUsuarioQuery)) {
                updateUsuarioStmt.setInt(1, usuario.tipoUsuario().id_tipo());
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
    
    // Delete Usuario By ID
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
    
}
