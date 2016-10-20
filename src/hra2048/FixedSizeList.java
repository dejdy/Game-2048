/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hra2048;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author dejdy
 *
 */
public class FixedSizeList {

    private final int SIZE;
    private final List<Pair<HashMap<Location, Tile>, Integer>> list;

    public FixedSizeList(int size) {
        SIZE = size;
        list = new ArrayList<>();
    }

    public void addGrid(HashMap gr, Integer pastScore) {
        if (list.size() >= SIZE) {
            list.remove(0);
        }
        Pair<HashMap<Location, Tile>, Integer> newPair = new Pair<>(gr, pastScore);
        list.add(newPair);
    }

    public Pair<HashMap<Location, Tile>, Integer> getVal() {
        if (list.size() <= 0) {
            return list.get(0);
        } else {
            return list.get(list.size() - 1);
        }
    }

    public int getSize() {
        return list.size();
    }

    void remLast() {
        if (list.size() > 0) {
            list.remove(list.size() - 1);
        }

    }

    void clear() {
        list.clear();
    }

}
