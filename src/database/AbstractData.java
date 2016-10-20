/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dejdy
 */
public abstract class AbstractData {
    
    protected PlayerDAO playerDAO;
    
    protected Connection connection;

    public abstract PlayerDAO getPlayerDAO();


    public AbstractData() {
        playerDAO = null;
        connection = null;
    }


    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(AbstractData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
