/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.entities;

/**
 *
 * @author dejdy
 */
public class Player {
    private final int id;
    private final String name;
    private int highScore;

    public Player(int id, String name, int highScore) {        
        this.id = id;
        this.name = name;
        this.highScore = highScore;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public int getHighScore() {
        return highScore;
    }
}
