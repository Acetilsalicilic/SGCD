/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Records.Cita;
import Records.Lugar;
import Records.Medico;
import Records.Paciente;
import Records.Sucursal;
import interfaces.DAO;

/**
 *
 * @author W10
 */
public class ProxyDAO implements DAO {

    @Override
    public Sucursal getSucursal(String ID) {
        return new Sucursal("1", "CDMX", new Lugar("id", "dir", "ciudad"));
    }

    @Override
    public Medico getMedico(String ID) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Paciente getPaciente(String ID) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Lugar getLugar(String ID) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Cita getCita(String ID) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
