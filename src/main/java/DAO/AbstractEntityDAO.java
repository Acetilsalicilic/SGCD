/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import interfaces.SQLQuery;
import interfaces.SQLUpdate;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author W10
 */
public class AbstractEntityDAO {
    private Connection con;
    public AbstractEntityDAO(Connection con) {
        this.con = con;
    }
    
    protected Object inStatementQuery(SQLQuery query) {
        try (var st = con.createStatement()) {
            return query.doQuery(st);
        } catch (SQLException e) {
            System.out.println("ERROR: Error while trying to query:" + e);
            return null;
        }
    }
    
    protected int inStatementUpdate(SQLUpdate update) {
        try (var st = con.createStatement()) {
            return update.doUpdate(st);
        } catch (SQLException e) {
            System.out.println("ERROR: Error while trying to update:" + e);
            return -1;
        }
    }
}
