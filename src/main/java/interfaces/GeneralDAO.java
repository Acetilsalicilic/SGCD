/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import Records.Cita;
import Records.Medico;
import Records.Paciente;
import Records.Usuario;

/**
 *
 * @author W10
 */
public interface GeneralDAO {
    public Medico getMedico(String ID);
    public Paciente getPaciente(String ID);
    public Cita getCita(String ID);
    public Usuario getUsuario(String usuario);
}
