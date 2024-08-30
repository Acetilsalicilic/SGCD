/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Records.Cita;
import Records.Medico;
import Records.Paciente;
import Records.Usuario;
import interfaces.GeneralDAO;
import interfaces.SQLQuery;
import interfaces.SQLUpdate;
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
        if (instance == null)
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
    private Object inStatementQuery(SQLQuery query) {
        try (var st = cn.createStatement()) {
            return query.doQuery(st);
        } catch (SQLException e) {
            System.out.println("ERROR: Error while trying to query:" + e);
            return null;
        } 
    }
    
    private void inStatementUpdate(SQLUpdate update) {
        try (var st = cn.createStatement()) {
            update.doUpdate(st);
        } catch (SQLException e) {
            System.out.println("ERROR: Error while trying to update:" + e);
        }
    }


//    @Override
//    public Sucursal getSucursal(String ID) {
//        return (Sucursal) inStatementQuery((st) -> {
//            String query = "SELECT sucursales.id AS suc_id, sucursales.nombre, lugares.id AS lug_id, lugares.ciudad, lugares.direccion FROM sucursales INNER JOIN lugares ON sucursales.lugar_id=lugares.id WHERE sucursales.id='"+ID+"';";
//            var rs = st.executeQuery(query);
//            
//            rs.next();
//        
//            String suc_id, nombre, lug_id, ciudad, direccion;
//
//            suc_id = rs.getString("suc_id");
//            nombre = rs.getString("nombre");
//            lug_id = rs.getString("lug_id");
//            ciudad = rs.getString("ciudad");
//            direccion = rs.getString("direccion");
//
//            return new Sucursal(suc_id, nombre, new Lugar(lug_id, ciudad, direccion));
//        });
//    }

    @Override
    public Medico getMedico(String ID) {
        return (Medico) inStatementQuery((st) -> {
            return null;
        });
    }

    @Override
    public Paciente getPaciente(String ID) {
        return (Paciente) inStatementQuery((st) -> {
            return null;
        });
    }

//    @Override
//    public Lugar getLugar(String ID) {
//        return (Lugar) inStatementQuery((st) -> {
//            String query = "SELECT * FROM lugares WHERE id='"+ID+"';";
//            var rs = st.executeQuery(query);
//            
//            rs.next();
//            
//            //Parse
//            
//            String id, direccion, ciudad;
//            
//            id = rs.getString("id");
//            direccion = rs.getString("direccion");
//            ciudad = rs.getString("ciudad");
//            
//            return new Lugar(id, direccion, ciudad);
//        });
//    }

    @Override
    public Cita getCita(String ID) {
        return (Cita) inStatementQuery((st) -> {
            var rs = st.executeQuery("SELECT * FROM citas WHERE id='" + ID + "';");
            rs.next();
            
            return null;
        });
    }

//    @Override
//    public boolean addSucursal(Sucursal sucursal) {
//        return inPreparedUpdate(() -> {
//            var stmt = cn.prepareStatement("INSERT INTO sucursales VALUES (?, ?, ?)");
//            
//            stmt.setString(1, sucursal.ID());
//            stmt.setString(2, sucursal.lugar().id());
//            stmt.setString(3, sucursal.nombre());
//            
//            stmt.executeUpdate();
//            stmt.close();
//        });
//    }

    @Override
    public Usuario getUsuario(String usuario) {
        return (Usuario) inStatementQuery((st) -> {
            var rs = st.executeQuery("SELECT * FROM usuarios WHERE usuario='" + usuario + "';");
            
            rs.next();
            
            return new Usuario(rs.getString("usuario"), rs.getString("contra"), rs.getString("tipo"));
        });
    }
}
