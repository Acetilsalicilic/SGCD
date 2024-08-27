/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import Records.Cita;
import Records.Lugar;
import Records.Medico;
import Records.Paciente;
import Records.Sucursal;

/**
 *
 * @author W10
 */
public interface GeneralDAO {
    public Sucursal getSucursal(String ID);
    public Medico getMedico(String ID);
    public Paciente getPaciente(String ID);
    public Lugar getLugar(String ID);
    public Cita getCita(String ID);
    
    public boolean addSucursal(Sucursal sucursal);
}
