/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package Records;

/**
 *
 * @author W10
 */
public record Medico(Integer id_medico, Usuario usuario, Especialidad especialidad, String nombre_medico, String apellidos_medico) {

    public Medico(Integer id_medico, Usuario usuario, Especialidad especialidad, String nombre_medico, String apellidos_medico) {
        this.id_medico = id_medico;
        this.usuario = usuario;
        this.especialidad = especialidad;
        this.nombre_medico = nombre_medico;
        this.apellidos_medico = apellidos_medico;
    }

    // Constructor with no id, for the database creates it's own
    public Medico(Usuario usuario, Especialidad especialidad, String nombre_medico, String apellidos_medico) {
        this(null, usuario, especialidad, nombre_medico, apellidos_medico);
    }

}
