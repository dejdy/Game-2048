/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author dejdy
 */
public class SQLCommands {

    public static final String ADD = "INSERT INTO PLAYERS VALUES(DEFAULT, ?, ?)";
    public static final String DELETE = "DELETE FROM PLAYERS WHERE ID = ?";
    public static final String GET_ALL = "SELECT PLAYERS.ID, PLAYERS.NAME, PLAYERS.HIGHSCORE FROM PLAYERS ORDER BY PLAYERS.HIGHSCORE DESC";


}
