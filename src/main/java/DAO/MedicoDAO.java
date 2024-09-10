/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Records.Medico;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 *
 * @author W10
 */
public class MedicoDAO extends AbstractEntityDAO {
    
    public MedicoDAO(Connection con) {
        super(con);
    }
    
//    public ArrayList<Medico> getAll() {
//        return (ArrayList<Medico>) inStatementQuery((st) -> {
//            var medicos = new ArrayList<Medico>();
//            
//            var rs = st.executeQuery("select\n" +
//                "	medicos.id_medico as id,\n" +
//                "	medicos.id_usuario as id_usuario,\n" +
//                "	medicos.nombre as nombre,\n" +
//                "	medicos.apellidos as apellidos,\n" +
//                "	especialidades.desc_espe as especialidad\n" +
//                "from\n" +
//                "	medicos \n" +
//                "inner join\n" +
//                "	especialidades\n" +
//                "	on medicos.id_especialidad = especialidades.id_especialidad");
//            
//            while (rs.next()) {
//                var usuario = EntityDAOPool.instance().getUsuarioDAO().getById(rs.getInt("id_usuario"));
//                medicos.add(new Medico(
//                        rs.getInt("id"),
//                        rs.getString("nombre"),
//                        rs.getString("apellidos"),
//                        rs.getString("especialidad"),
//                        usuario
//                ));
//            }
//            
//            return null;
//        });
//    }
//    
//    public Medico getById(int id) {
//        return (Medico) inStatementQuery((st) -> {
//            var rs = st.executeQuery("select\n" +
//                "	medicos.id_medico as id,\n" +
//                "	medicos.id_usuario as id_usuario,\n" +
//                "	medicos.nombre as nombre,\n" +
//                "	medicos.apellidos as apellidos,\n" +
//                "	especialidades.desc_espe as especialidad\n" +
//                "from\n" +
//                "	medicos \n" +
//                "inner join\n" +
//                "	especialidades\n" +
//                "	on medicos.id_especialidad = especialidades.id_especialidad\n" +
//                "where medicos.id_medico = " + id + ";");
//            
//            rs.next();
//            
//            int id_usuario = rs.getInt("id_usuario");
//            
//            var usuario = EntityDAOPool.instance().getUsuarioDAO().getById(id_usuario);
//            
//            return new Medico(
//                    rs.getInt("id"),
//                    rs.getString("nombre"),
//                    rs.getString("apellidos"),
//                    rs.getString("especialidad"),
//                    usuario
//            );
//        });
//    }
//    
//    public ArrayList<Medico> getByNombre(String nombre) {
//        var medicos = getAll();
//            
//        var filteredMedicos = medicos.stream().filter(
//                medico -> medico.nombre().toLowerCase().contains(nombre.toLowerCase())
//        );
//            
//        return filteredMedicos.collect(Collectors.toCollection(ArrayList::new));
//    }
//    
//    public ArrayList<Medico> getByEspecialidad(String especialidad) {
//        var medicos = getAll();
//            
//        var filteredMedicos = medicos.stream().filter(
//                medico -> medico.especialidad().toLowerCase().contains(especialidad.toLowerCase())
//        );
//            
//        return filteredMedicos.collect(Collectors.toCollection(ArrayList::new));
//    }
//    
//    public int update(Medico medico) {
//        return preparedUpdate((con) -> {
//            var st = con.prepareStatement("UPDATE medicos SET id_medico=?, id_usuario=?, id_especialidad=?, nombre=?, apellidos=?");
//            int id_espe;
//            
//            try (var stmt = con.createStatement()) {
//                var rs = stmt.executeQuery("SELECT id_especialidad FROM especialidades WHERE desc_espe='" + medico.especialidad() + "';");
//                rs.next();
//                id_espe = rs.getInt("id_especialidad");
//            }
//            
//            st.setInt(1, medico.id());
//            st.setInt(2, medico.usuario().id_usuario());
//            st.setInt(3, id_espe);
//            st.setString(4, medico.nombre());
//            st.setString(5, medico.apellidos());
//            
//            st.execute();
//            
//            return 1;
//        });
//    }
//    
//    public int deleteById(int id) {
//        return inStatementUpdate((st) -> {
//            return st.executeUpdate("DELETE FROM medicos WHERE id_medico=" + id + ";");
//        });
//    }
//    
//    public int create(Medico medico) {
//        return preparedUpdate((con) -> {
//           var st = con.prepareStatement("INSERT INTO medicos VALUES (?, ?, ?, ?, ?);");
//           
//           int id_esp;
//           
//           try (var stmt = con.createStatement()) {
//               var rs = stmt.executeQuery("SELECT id_especialidad FROM especialidades WHERE especialidad");
//               rs.next();
//               id_esp = rs.getInt("id_especialidad");
//           }
//           
//           st.setInt(1, medico.id());
//           st.setInt(2, medico.usuario().id_usuario());
//           st.setInt(3, id_esp);
//           st.setString(4, medico.nombre());
//           st.setString(5, medico.apellidos());
//           
//           st.execute();
//            
//            return 1; 
//        });
//    }
}
