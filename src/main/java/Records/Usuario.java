/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package Records;

/**
 *
 * @author W10
 */
public record Usuario(Integer id_usuario, TipoUsuario tipoUsuario, String nombre_usuario, String contrasena) {
    
    // Constructor Principal 
    public Usuario(Integer id_usuario, TipoUsuario tipoUsuario, String nombre_usuario, String contrasena) {
        this.id_usuario = id_usuario;
        this.tipoUsuario = tipoUsuario;
        this.nombre_usuario = nombre_usuario;
        this.contrasena = contrasena;
    }

    // Constructor with no id
    public Usuario(TipoUsuario tipo_usuario, String nombre_usuario, String contrasena) {
        this(null, tipo_usuario, nombre_usuario, contrasena);
    }
}
