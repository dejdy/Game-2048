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
public class Score {

    private Integer scoreInt;
    private final Text score;

    public Score() {
        score = new Text("0");
        scoreInt = 0;
        score.setFont(Font.font("Helvetica Neue", FontWeight.MEDIUM, 35));
    }

    public Text getScore() {
        return score;
    }

    public Integer getScoreInt() {
        return scoreInt;
    }

    public void setScore(Integer newScore) {
        scoreInt = newScore;
        score.setText(scoreInt.toString());
    }

    public void addScore(Integer newScore) {
        scoreInt += newScore;
        score.setText(scoreInt.toString());
    }

}
