/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import database.entities.Player;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dejdy
 */
public class PlayerDAO {

    protected PreparedStatement addPs;
    protected PreparedStatement getAllPs;
    protected PreparedStatement deletePs;

    public PlayerDAO(Connection connection) {
        try {

            addPs = connection.prepareStatement(database.SQLCommands.ADD);
            getAllPs = connection.prepareStatement(database.SQLCommands.GET_ALL);
            deletePs = connection.prepareStatement(database.SQLCommands.DELETE);
        } catch (SQLException ex) {
            Logger.getLogger(PlayerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public Collection<Player> getAll() {
        List<Player> ret = new ArrayList<>();
        try {
            ResultSet rs = getAllPs.executeQuery();
            while (rs.next()) {
                ret.add(new Player(rs.getInt(1), rs.getString(2), rs.getInt(3)));              
            }
            return ret;
        } catch (SQLException ex) {
            Logger.getLogger(PlayerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    
    public void deletePlayer(int id) {
        try {
            deletePs.setInt(1, id);
            deletePs.execute();
        } catch (SQLException ex) {
            Logger.getLogger(PlayerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        public void addPlayer(Player p) {
        try {
            addPs.setString(1, p.getName());
            addPs.setInt(2, p.getHighScore());
            addPs.execute();
        } catch (SQLException ex) {
            Logger.getLogger(PlayerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
