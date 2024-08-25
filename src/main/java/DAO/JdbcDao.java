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
import interfaces.GeneralDAO;
import java.sql.Connection;
import java.sql.SQLException;
import nativeDB.MySQLConnection;

/**
 *
 * @author W10
 */
public class JdbcDao implements GeneralDAO {
    private static JdbcDao instance;
    private MySQLConnection connection;
    private Connection cn;
    
    public static void init(String url, String username, String password) {
        var cn = new MySQLConnection(url, username, password);
        instance = new JdbcDao(cn);
    }
    
    private JdbcDao(MySQLConnection cn) {
        this.connection = cn;
        this.cn = connection.connection();
    }
    
    public static JdbcDao instance() {
        return instance;
    }

    @Override
    public Sucursal getSucursal(String ID) {
        try (var st = cn.createStatement()) {
            String query = "SELECT sucursales.id AS suc_id, sucursales.nombre, lugares.id AS lug_id, lugares.ciudad, lugares.direccion FROM sucursales INNER JOIN lugares ON sucursales.lugar_id=lugares.id WHERE sucursales.id="+ID+";";
            var rs = st.executeQuery(query);
            
            rs.next();
        
            String suc_id, nombre, lug_id, ciudad, direccion;

            suc_id = rs.getString("suc_id");
            nombre = rs.getString("nombre");
            lug_id = rs.getString("lug_id");
            ciudad = rs.getString("ciudad");
            direccion = rs.getString("direccion");

            return new Sucursal(suc_id, nombre, new Lugar(lug_id, ciudad, direccion));
        
        } catch(SQLException e) {
            return null;
        }
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

    @Override
    public void addSucursal(Sucursal sucursal) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
