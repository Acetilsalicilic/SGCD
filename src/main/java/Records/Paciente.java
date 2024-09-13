/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package Records;

/**
 *
 * @author W10
 */
public record Paciente(Integer id_paciente, Usuario usuario, String nombre, String apellidos, String telefono, String direccion) {

    // Main constructor
    public Paciente(Integer id_paciente, Usuario usuario, String nombre, String apellidos, String telefono, String direccion) {
        this.id_paciente = id_paciente;
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    // Constructor with no ID
    public Paciente(String nombre, String apellidos, String telefono, String direccion) {
        this(null, null, nombre, apellidos, telefono, direccion);
    }
}
