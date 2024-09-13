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
        tipoUsuarioDao = new TipoUsuarioDAO(con);
        pacienteDao = new PacienteDAO(con);
        medicoDao = new MedicoDAO(con);
        servicioDao = new ServicioDAO(con);
        especialidadDao = new EspecialidadDAO(con);
        citaDao = new CitaDAO(con);
        
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
    private TipoUsuarioDAO tipoUsuarioDao;
    private PacienteDAO pacienteDao;
    private MedicoDAO medicoDao;
    private ServicioDAO servicioDao;
    private EspecialidadDAO especialidadDao;
    private CitaDAO citaDao;
    
    //--------------Get DAO Methods-------------
    public UsuarioDAO getUsuarioDAO() {
        return usuarioDao;
    }
    
    public TipoUsuarioDAO getTipoUsuarioDAO() {
        return tipoUsuarioDao;
    }
    
    public PacienteDAO getPacienteDAO() {
        return pacienteDao;
    }
    
    public MedicoDAO getMedicoDAO() {
        return medicoDao;
    }
    
    public ServicioDAO getServicioDAO() {
        return servicioDao;
    }
    
    public EspecialidadDAO getEspecialidadDAO() {
        return especialidadDao;
    }
    
    public CitaDAO getCitaDAO() {
        return citaDao;
    }
}
