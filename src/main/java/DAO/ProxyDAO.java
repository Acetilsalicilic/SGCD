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
import java.util.ArrayList;

/**
 *
 * @author W10
 */
public class ProxyDAO implements DAO {

    @Override
    public Sucursal getSucursal(String ID) {
        ArrayList<Sucursal> sucList = new ArrayList<>();
        sucList.add(new Sucursal("1", "primero", new Lugar("id", "direccion", "ciudad")));
        sucList.add(new Sucursal("2", "segundo", new Lugar("id", "direccion", "ciudad")));
        sucList.add(new Sucursal("3", "tercero", new Lugar("id", "direccion", "ciudad")));
        
        for (var sucursal: sucList) {
            if (sucursal.ID().equals(ID)) return sucursal;
        }
        
        return new Sucursal(null, null, null);
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
