/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hra2048;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author dejdy
 */
public class Moves {

    private final Text moves;
    private Integer movesInt = 0;

    public Moves() {
        moves = new Text(movesInt.toString());
        moves.setFont(Font.font("Helvetica Neue", FontWeight.MEDIUM, 35));
    }

    public void dec() {
        movesInt -= 1;
        moves.setText(movesInt.toString());
    }

    public void inc() {
        movesInt += 1;
        moves.setText(movesInt.toString());
    }

    public Text getMoves() {
        return moves;
    }

    public Integer getMovesInt() {
        return movesInt;
    }

    public void setMoves(Integer newMoves) {
        movesInt = newMoves;
        moves.setText(movesInt.toString());
    }
}
