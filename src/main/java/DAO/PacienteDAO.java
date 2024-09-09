/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Records.Paciente;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 *
 * @author W10
 */
public class PacienteDAO extends AbstractEntityDAO {
    
    public PacienteDAO(Connection con) {
        super(con);
    }
    
    public ArrayList<Paciente> getAll() {
        return (ArrayList<Paciente>) inStatementQuery((st) -> {
            var pacientes = new ArrayList<Paciente>();
            
            var rs = st.executeQuery("SELECT * FROM pacientes");
            
            while(rs.next()) {
                
                var usuario = EntityDAOPool.instance().getUsuarioDAO().getById(rs.getInt("id_usuario"));
                
                pacientes.add(new Paciente(
                        rs.getInt("id_paciente"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("telefono"),
                        rs.getString("direccion"),
                        usuario
                ));
            }
            
            return pacientes;
        });
    }
    
    public Paciente getById(int id) {
        return (Paciente) inStatementQuery((st) -> {
            
            var rs = st.executeQuery("SELECT * FROM pacientes WHERE id_paciente=" + id + ";");
            rs.next();
            
            var usuario = EntityDAOPool.instance().getUsuarioDAO().getById(rs.getInt("id_usuario"));
            
            return new Paciente(
                    rs.getInt("id_paciente"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getString("telefono"),
                    rs.getString("direccion"),
                    usuario
            );
        });
    }
    
    public ArrayList<Paciente> getByNombre(String nombre) {
        return (ArrayList<Paciente>) inStatementQuery((st) -> {
            var pacientes = getAll();
            
            var filteredPacientes = pacientes.stream().filter(paciente -> paciente.nombre().contains(nombre));
            return filteredPacientes.collect(Collectors.toCollection(ArrayList::new));
        });
    }
    
    public int create(Paciente paciente) {
        return preparedUpdate((con) -> {
            var st = con.prepareStatement("INSERT INTO pacientes VALUES (?, ?, ?, ?, ?, ?);");
            
            st.setInt(1, paciente.id());
            st.setInt(2, paciente.usuario().id());
            st.setString(3, paciente.nombre());
            st.setString(4, paciente.apellidos());
            st.setString(5, paciente.telefono());
            st.setString(6, paciente.direccion());
            
            st.execute();
            
            return 1;
        });
    }
    
    public int deleteById(int id) {
        return inStatementUpdate((st) -> {
            return st.executeUpdate("DELETE FROM pacientes WHERE id_paciente='" + id + "';");
        });
    }
    
    public int update(Paciente paciente) {
        return preparedUpdate((con) -> {
            var st = con.prepareStatement("UPDATE pacientes SET id_paciente=?, id_usuario=?, nombre=?, apellidos=?, telefono=?, direccion=? WHERE id_paciente=?;");

            st.setInt(1, paciente.id());
            st.setInt(2, paciente.usuario().id());
            st.setString(3, paciente.nombre());
            st.setString(4, paciente.apellidos());
            st.setString(5, paciente.telefono());
            st.setString(6, paciente.direccion());
            
            st.execute();
            
            return 1;
        });
    }
}
