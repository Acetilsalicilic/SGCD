/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package Records;

import java.time.LocalDateTime;

/**
 *
 * @author W10
 */
public record Cita(Integer id_cita, Medico medico, Paciente paciente, Servicio servicio, LocalDateTime fecha_cita) {

}
