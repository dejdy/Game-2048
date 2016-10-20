/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.jdbc.EmbeddedDataSource;

/**
 *
 * @author dejdy
 */
public class DerbyData extends AbstractData {


    protected PlayerDAO playerDAO;

    public DerbyData(String url, String username, String pass) {
        super();
        try {
            EmbeddedDataSource eds = new EmbeddedDataSource();
            eds.setDatabaseName(url + "/embeddeddb");
            eds.setCreateDatabase("create");
            eds.setUser(username);
            eds.setPassword(pass);
            connection = eds.getConnection();
            playerDAO = null;
            init();
        } catch (SQLException ex) {
            Logger.getLogger(DerbyData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    private void init() {
        try {
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getTables(null, null, "PLAYERS", null);
            if (!rs.next()) {
                Statement s = connection.createStatement();
                s.executeUpdate("CREATE TABLE PLAYERS"
                        + "(ID INT NOT NULL GENERATED ALWAYS AS IDENTITY,"
                        + "NAME VARCHAR(50),"
                        + "HIGHSCORE INT,"
                        + "PRIMARY KEY (ID))");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DerbyData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    @Override
    public PlayerDAO getPlayerDAO() {
        if (playerDAO == null) {
            playerDAO = new PlayerDAO(connection);
        }
        return playerDAO;
    }
}