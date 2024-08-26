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
import interfaces.SQLQuery;
import java.sql.Connection;
import java.sql.SQLException;
import nativeDB.MySQLConnection;

/**
 *
 * @author W10
 */
public class JdbcDao implements GeneralDAO {
    private static JdbcDao instance;
    private MySQLConnection mySqlConnection;
    private Connection cn;
    
    //-----------------------SINGLETON METHODS---------------------------
    private JdbcDao(String url, String username, String password) {
        mySqlConnection = new MySQLConnection(url, username, password);
        cn = mySqlConnection.connection();
    }
    
    public static void init(String url, String username, String password) {
        instance = new JdbcDao(url, username, password);
    }
    
    public static void close() {
        instance.closeInstance();
    }
    
    private void closeInstance() {
        mySqlConnection.close();
    }
    
    public static JdbcDao instance() {
        return instance;
    }
    
    //----------------------------------ACTUAL DATA ACCESS LOGIC---------------
    private Object inStatement(SQLQuery query) {
        try (var st = cn.createStatement()) {
            return query.doQuery(st);
        } catch (SQLException e) {
            System.out.println("ERROR: Error while trying to query:" + e);
            return null;
        } 
    }

    @Override
    public Sucursal getSucursal(String ID) {
        return (Sucursal) inStatement((st) -> {
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
        });
    }

    @Override
    public Medico getMedico(String ID) {
        return (Medico) inStatement((st) -> {
            var query = "select \n" +
                "	medicos.id as med_id, \n" +
                "	medicos.nombre as med_nom, \n" +
                "	medicos.especialidad, \n" +
                "	sucursales.id as suc_id, \n" +
                "	sucursales.nombre as suc_nom,\n" +
                "	lugares.id as lug_id,\n" +
                "	lugares.direccion,\n" +
                "	lugares.ciudad\n" +
                "from medicos \n" +
                "inner join \n" +
                "	sucursales on sucursales.id = medicos.id_sucursal\n" +
                "inner join\n" +
                "	lugares on sucursales.lugar_id = lugares.id\n" +
                "where medicos.id=" + ID + ";";
            
            var rs = st.executeQuery(query);
            rs.next();
            
            return new Medico(
                    rs.getString("med_id"),
                    rs.getString("med_nom"),
                    rs.getString("especialidad"),
                    new Sucursal(
                            rs.getString("suc_id"),
                            rs.getString("suc_nom"),
                            new Lugar(
                                    rs.getString("lug_id"),
                                    rs.getString("direccion"),
                                    rs.getString("ciudad")
                            )
                    )
            );
        });
    }

    @Override
    public Paciente getPaciente(String ID) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Lugar getLugar(String ID) {
        return (Lugar) inStatement((st) -> {
            String query = "SELECT * FROM lugares WHERE id="+ID+";";
            var rs = st.executeQuery(query);
            
            rs.next();
            
            //Parse
            
            String id, direccion, ciudad;
            
            id = rs.getString("id");
            direccion = rs.getString("direccion");
            ciudad = rs.getString("ciudad");
            
            return new Lugar(id, direccion, ciudad);
        });
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
