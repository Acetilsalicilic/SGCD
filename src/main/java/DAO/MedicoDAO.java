/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Records.Medico;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
    
    public Medico getById(Integer id_medico) {
        return (Medico) inStatementQuery((st) -> {
            String medicoByIdQuery =
                "SELECT " + 
                "medicos.id_medico, " + 
                "medicos.id_usuario, " + 
                "medicos.id_especialidad, " +
                "medicos.nombre AS nombre_medico, " +
                "medicos.apellidos AS apellidos_medico, " +
                "especialidades.id_especialidad, " +
                "especialidades.desc_espe " + 
                "FROM medicos " + 
                "INNER JOIN especialidades ON medicos.id_especialidad = especialidades.id_especialidad " +
                "WHERE medicos.id_usuario = ?;"
            ;
            try (PreparedStatement medicoByIdStmt = st.getConnection().prepareStatement(medicoByIdQuery)) {
                medicoByIdStmt.setInt(1, id_medico);
                
                try (var medicoById = medicoByIdStmt.executeQuery()) {
                    if (medicoById.next()) {
                        var usuario = EntityDAOPool.instance().getUsuarioDAO().getById(medicoById.getInt("id_usuario"));
                        var especialidad = EntityDAOPool.instance().getEspecialidadDAO().getTypeEspecialidad(medicoById.getInt("id_especialidad"));
                        return new Medico (
                            medicoById.getInt("id_medico"),
                            usuario,
                            especialidad,
                            medicoById.getString("nombre_medico"),
                            medicoById.getString("apellidos_medico")
                        );
                    } else {
                        return null;
                    }
                }
            }
        });
    }
    
    public ArrayList<Medico> getAll() {
        return (ArrayList<Medico>) inStatementQuery((st) -> {
            var medicos = new ArrayList<Medico>();
            String medicosAllQuery = 
                "SELECT " + 
                "medicos.id_medico, " + 
                "medicos.id_usuario, " + 
                "medicos.id_especialidad, " +
                "medicos.nombre AS nombre_medico, " +
                "medicos.apellidos AS apellidos_medico, " +
                "especialidades.id_especialidad, " +
                "especialidades.desc_espe " + 
                "FROM medicos " + 
                "INNER JOIN especialidades ON medicos.id_especialidad = especialidades.id_especialidad;"
            ;
            try (PreparedStatement medicosAllStmt = st.getConnection().prepareStatement(medicosAllQuery)) {
                try (var medicosAll = medicosAllStmt.executeQuery()){
                    while(medicosAll.next()){
                        var usuario = EntityDAOPool.instance().getUsuarioDAO().getById(medicosAll.getInt("id_usuario"));
                        var especialidad = EntityDAOPool.instance().getEspecialidadDAO().getTypeEspecialidad(medicosAll.getInt("id_especialidad"));
                        medicos.add(
                            new Medico (
                                medicosAll.getInt("id_medico"),
                                    usuario,
                                especialidad,
                                medicosAll.getString("nombre_medico"),
                                medicosAll.getString("apellidos_medico")
                        ));
                    }
                    return medicos;
                }
            }
        });
    }
    
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
