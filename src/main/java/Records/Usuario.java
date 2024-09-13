/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package Records;

/**
 *
 * @author W10
 */
public record Usuario(Integer id_usuario, Integer id_tipo_usuario, String nombre_usuario, String contrasena, String desc_tipo) {
    
    // Constructor Principal 
    public Usuario(Integer id_usuario, Integer id_tipo_usuario, String nombre_usuario, String contrasena, String desc_tipo) {
        this.id_usuario = id_usuario;
        this.id_tipo_usuario = id_tipo_usuario;
        this.nombre_usuario = nombre_usuario;
        this.contrasena = contrasena;
        this.desc_tipo = desc_tipo != null ? desc_tipo : "";
    }
    
    // Constructor Secundario 
    public Usuario(Integer id_usuario, Integer id_tipo_usuario, String nombre_usuario, String contrasena) {
        this(id_usuario, id_tipo_usuario, nombre_usuario, contrasena, "");
    }
    
    // Constructor Secundario 
    public Usuario(Integer id_usuario, Integer id_tipo_usuario) {
        this(id_usuario, id_tipo_usuario, "", "", "");
    }
    
}
