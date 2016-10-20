/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hra2048;

import static hra2048.Hra2048.HISTORY_LENGTH;
import java.util.HashMap;
import javafx.util.Pair;

/**
 *
 * @author dejdy
 */
public class GameHistory {

    private final FixedSizeList history;

    public GameHistory() {
        history = new FixedSizeList(HISTORY_LENGTH);
    }

    public void addHist(HashMap grid, Integer pastScore) {
        history.addGrid(grid, pastScore);
    }

    public int getSize() {
        return history.getSize();
    }

    public Pair<HashMap<Location, Tile>, Integer> getPrev() {
        Pair<HashMap<Location, Tile>, Integer> temp;
        temp = history.getVal();
        if (history.getSize() > 1) {
            history.remLast();
        }
        return temp;
    }

    public void clear() {
        history.clear();
    }

}
