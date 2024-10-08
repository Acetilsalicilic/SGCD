/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Records.Servicio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 *
 * @author Vega
 */
public class ServicioDAO extends AbstractEntityDAO {
    
    public ServicioDAO(Connection con) {
        super(con);
    }
    
    public Servicio getTypeService(Integer id_servicio) {
        return (Servicio) inStatementQuery((st) -> {
            String tipoServicioQuery = "SELECT * FROM servicios WHERE id_servicio = ?;";
            try (PreparedStatement tipoServicioStmt = st.getConnection().prepareStatement(tipoServicioQuery)) {
                tipoServicioStmt.setInt(1, id_servicio);

                try (var tipoServicio = tipoServicioStmt.executeQuery()) {
                    if (tipoServicio.next()){
                        return new Servicio(
                            tipoServicio.getInt("id_servicio"),
                            tipoServicio.getString("desc_servicio")
                        );
                    } else {
                        return null; 
                    }
                }
            }
        });
    }

    public ArrayList<Servicio> getAll() {
        return (ArrayList<Servicio>) inStatementQuery((st) -> {
            var rs = st.executeQuery("SELECT * FROM servicios;");
            var servicios = new ArrayList<Servicio>();
            while (rs.next()) {
                servicios.add(new Servicio(
                        rs.getInt("id_servicio"),
                        rs.getString("desc_servicio")
                ));
            }

            return servicios;
        });
    }
}
