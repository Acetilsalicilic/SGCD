/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import nativeDB.MySQLConnection;

/**
 *
 * @author W10
 */
public class EntityDAOPool {
    private static EntityDAOPool instance;
    private Connection con;
    private MySQLConnection mySqlConnection;
    
    public static void init(String url, String username, String password) {
        instance = new EntityDAOPool(url, username, password);
    }
    
    private EntityDAOPool (String url, String username, String password) {
        mySqlConnection = new MySQLConnection(url, username, password);
        con = mySqlConnection.connection();
        
        //------------DAO Instantiation-----------
        usuarioDao = new UsuarioDAO(con);
        pacienteDao = new PacienteDAO(con);
        medicoDao = new MedicoDAO(con);

    }
    
    public static void close() {
        instance.closeInstance();
    }
    
    private void closeInstance() {
        mySqlConnection.close();
    }
    
    public static EntityDAOPool instance() {
        if (instance == null) {
            System.out.println("ERROR! Pool hasn't been instantiated yet!");
        }
        return instance;
    }
    
    //---------------DAO Variables--------------
    
    private UsuarioDAO usuarioDao;
    private PacienteDAO pacienteDao;
    private MedicoDAO medicoDao;
    
    //--------------Get DAO Methods-------------
    public UsuarioDAO getUsuarioDAO() {
        return usuarioDao;
    }
    
    public PacienteDAO getPacienteDAO() {
        return pacienteDao;
    }
    
    public MedicoDAO getMedicoDAO() {
        return medicoDao;
    }
}
