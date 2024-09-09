/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author W10
 */
public interface PreparedSQLUpdate {
    public int doUpdate(Connection con) throws SQLException;
}
