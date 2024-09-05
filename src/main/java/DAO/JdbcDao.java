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
    
    private int inStatementUpdate(SQLUpdate update) {
        try (var st = cn.createStatement()) {
           return update.doUpdate(st);
        } catch (SQLException e) {
            System.out.println("ERROR: Error while trying to update:" + e);
            return -1;
        }
    }

    @Override
    public Medico getMedico(String ID) {
        return (Medico) inStatementQuery((st) -> {
            return null;
        });
    }

    @Override
    public Paciente getPaciente(String ID) {
        return (Paciente) inStatementQuery((st) -> {
            var rs = st.executeQuery("SELECT * FROM pacientes WHERE id='" + ID + "';");
            rs.next();
            
            String usuarioNombre = rs.getString("usuario");
            Usuario usuario = getUsuario(usuarioNombre);
            
            return new Paciente(
                    rs.getString("id"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getString("telefono"),
                    rs.getString("direccion"),
                    usuario
            );
        });
    }


    @Override
    public Cita getCita(String ID) {
        return (Cita) inStatementQuery((st) -> {
            var rs = st.executeQuery("SELECT * FROM citas WHERE id='" + ID + "';");
            rs.next();
            
            return null;
        });
    }


    @Override
    public Usuario getUsuario(String usuario) {
        return (Usuario) inStatementQuery((st) -> {
            var rs = st.executeQuery("SELECT * FROM usuarios WHERE usuario='" + usuario + "';");
            
            rs.next();
            
            return new Usuario(rs.getString("usuario"), rs.getString("contra"), rs.getString("tipo"));
        });
    }
    
    //--------------------DELETE-----------------------------
    
    @Override
    public int deleteUsuario(String id) {
        return inStatementUpdate((st) -> {
            return st.executeUpdate("DELETE FROM usuarios WHERE usuario='" + id + "'");
        });
    }
    
    //--------------------MODIFY-----------------------------
    @Override
    public int modifyUsuario(Usuario usuario) {
        return 1;
    }
    //--------------------CREATE-----------------------------
    @Override
    public int createUsuario(Usuario usuario) {
        return 1;
    }
}
