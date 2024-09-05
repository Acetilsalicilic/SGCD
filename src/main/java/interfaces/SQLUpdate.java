/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author W10
 */
public interface SQLUpdate {
    public int doUpdate(Statement st) throws SQLException;
}
