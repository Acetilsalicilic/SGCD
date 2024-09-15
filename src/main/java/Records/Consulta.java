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
public record Consulta(Integer id_consulta, Medico medico, Paciente paciente, Servicio servicio, LocalDateTime fecha_consulta) {

}
