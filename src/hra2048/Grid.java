/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hra2048;

import static hra2048.Hra2048.marginSize;
import static hra2048.Hra2048.moves;
import static hra2048.Hra2048.score;
import static hra2048.Hra2048.squareSize;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.Pair;

/**
 *
 * @author dejdy
 */
public class Grid {

    private final Hra2048 application;
    private static final Duration ANIMATION_MOVE_TIME = Duration.millis(65);
    private final Map<Location, Tile> grid;
    private final Set<Location> usedLoc;
    public static volatile boolean finishedAnimation = true;
    public static volatile boolean finishedAnimation2 = true;
    private Integer highestTile = 0;
    StackPane gridStackPane = new StackPane();
    GridPane gridPane = new GridPane();
    ParallelTransition pt = new ParallelTransition();
    List<Pair<Location, Integer>> newValues = new ArrayList<>();
    List<Tile> toRemove = new ArrayList<>();
    HashMap<Location, Tile> tempMap;
    private final int BOARD_SIZE = 4;

    public Grid(Hra2048 application) {
        this.application = application;
        grid = new HashMap<>();
        usedLoc = new HashSet<>();
    }

    public Integer getHighestTile() {
        return highestTile;
    }
    
    public void createGrid(BorderPane layout) {
        HBox top = new HBox(115);
        Label movesLabel = new Label("moves");
        Label scoreLabel = new Label("score");
        movesLabel.setFont(Font.font("Century Gothic", 30));
        scoreLabel.setFont(Font.font("Century Gothic", 30));
        Label gameLogo = new Label("2048");
        gameLogo.setFont(Font.font("Century Gothic", FontWeight.BOLD, 50));
        GridPane scoreGrid = new GridPane();
        GridPane movesGrid = new GridPane();

        Text scoreText = score.getScore();
        Text movesText = moves.getMoves();
        scoreGrid.add(scoreLabel, 0, 0);
        scoreGrid.add(scoreText, 0, 1);
        movesGrid.add(movesLabel, 0, 0);
        movesGrid.add(movesText, 0, 1);
        GridPane.setHalignment(scoreText, HPos.CENTER);
        GridPane.setHalignment(movesText, HPos.CENTER);

        top.getChildren().addAll(movesGrid, gameLogo, scoreGrid);
        top.setAlignment(Pos.CENTER);
        layout.setTop(top);

        Rectangle backgRect;
        backgRect = new Rectangle(4 * Hra2048.squareSize + 15 * Hra2048.marginSize, 4 * Hra2048.squareSize + 15 * Hra2048.marginSize, Color.rgb(187, 173, 160));
        backgRect.setArcHeight(10);
        backgRect.setArcWidth(10);
        gridStackPane.getChildren().add(backgRect);

        layout.setCenter(gridStackPane);

        addTiles(layout);
    }

    public void addTiles(BorderPane layout) {
        gridPane.setAlignment(Pos.CENTER);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Tile tile = new Tile();
                gridPane.add(tile, i, j);
                GridPane.setMargin(tile, new Insets(marginSize, marginSize, marginSize, marginSize));
                grid.put(new Location(i, j), tile);
            }
        }
        gridStackPane.getChildren().add(gridPane);
    }

    public void updateGrid(Location loc, Integer val) {
        grid.get(loc).updateTile(val);
    }

    public void addRandomTile() {
        if (usedLoc.size() >= BOARD_SIZE * BOARD_SIZE) {
            return;
        }
        Random r = new Random();
        int newValue = 2;
        Location loc = new Location(r.nextInt(4), r.nextInt(4));
        while (usedLoc.contains(loc)) {
            loc = new Location(r.nextInt(4), r.nextInt(4));
        }

        if (r.nextInt(9) < 1) {
            newValue = 4;
        }
        usedLoc.add(loc);
        updateGrid(loc, newValue);
        finishedAnimation2 = false;
        ScaleTransition st = grid.get(loc).animateNewlyAddedTile();
        st.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                finishedAnimation2 = true;
            }
        });
        st.play();

    }

    private Location newPos(Location location, Direction direction) {
        Location farthest, previous;
        previous = location;
        do {
            farthest = location;
            location = farthest.offset(direction);
        } while (location.isValid() && !usedLoc.contains(location));

        return farthest;
    }

    public boolean canMerge(Location loc, Direction dir, Integer val) {
        Location shiftedLoc = loc.offset(dir);
        if (shiftedLoc.isValid() && val.equals(tempMap.get(shiftedLoc).getValue())) {
            return true;
        }
        return false;
    }

    private TranslateTransition addAnimation(Location loc1, Location loc2) {

        TranslateTransition tt = new TranslateTransition(ANIMATION_MOVE_TIME);
        Tile tile = new Tile(grid.get(loc1));
        tile.updateTile(grid.get(loc1).getValue());
        gridPane.add(tile, loc1.getX(), loc1.getY());
        tt.setNode(tile);
        tt.setFromX(0);
        tt.setFromX(0);
        tt.setByX((loc2.getX() - loc1.getX()) * (squareSize + marginSize * 2));
        tt.setByY((loc2.getY() - loc1.getY()) * (squareSize + marginSize * 2));
        toRemove.add(tile);

        return tt;
    }

    public boolean moveTiles(Direction direction, Location temp) {
        Integer val;
        boolean moved = false;
        Location temp2;
        boolean merge;
        val = tempMap.get(temp).getValue();

        if (usedLoc.contains(temp)) {
            temp2 = newPos(temp, direction);
            merge = canMerge(temp2, direction, val);
            if (merge) {
                val *= 2;
                temp2 = temp2.offset(direction);
                score.addScore(val);
            }
            if (!usedLoc.contains(temp2) || merge) {
                if(val>highestTile) {
                    highestTile=val;
                }
                moved = true;
                pt.getChildren().add(addAnimation(temp, temp2));
                Pair<Location, Integer> change1 = new Pair<>(temp2, val);
                Pair<Location, Integer> change2 = new Pair<>(temp, 0);
                tempMap.get(temp2).updateTile(val);
                tempMap.get(temp).updateTile(0);
                grid.get(temp).updateTile(0);
                newValues.add(change1);
                newValues.add(change2);
                if (merge) {
                    pt.getChildren().add(grid.get(temp2).animateMerge());
                }
                usedLoc.remove(temp);
                usedLoc.add(temp2);
            }
        }

        return moved;
    }

    private void finishMove(boolean moved, GameHistory history) {

            finishedAnimation = false;

        pt.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                /*for (int i = 0; i < newValues.size(); i++) {
                 grid.get(newValues.get(i).getKey()).updateTile(newValues.get(i).getValue());
                 }*/

                for (Tile entry : toRemove) {
                    gridPane.getChildren().remove(entry);
                }
                newValues.clear();
                Pair<HashMap<Location, Tile>, Integer> tempPair = new Pair(tempMap, score.getScoreInt());
                setGrid(tempPair);
                if (moved) {
                    addRandomTile();
                    history.addHist(copyMap(), score.getScoreInt());
                }
                pt = new ParallelTransition();
                    finishedAnimation = true;
                
            }
        });
        pt.play();
    }

    public boolean moveUp(GameHistory history) {

            if (!finishedAnimation) {
                return false;
            }
        
        tempMap = copyMap();
        Location temp, temp2;
        boolean moved = false;
        Integer val;
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                temp = new Location(i, j);
                if (moveTiles(Direction.UP, temp)) {
                    moved = true;
                }
            }
        }
        finishMove(moved, history);
        return moved;
    }

    public boolean moveDown(GameHistory history) {

            if (!finishedAnimation) {
                return false;
            }
        
        tempMap = copyMap();
        Location temp, temp2;
        boolean moved = false;
        Integer val;
        for (int i = 0; i < 4; i++) {
            for (int j = 2; j >= 0; j--) {
                temp = new Location(i, j);
                if (moveTiles(Direction.DOWN, temp)) {
                    moved = true;
                }
            }
        }
        finishMove(moved, history);
        return moved;
    }

    public boolean moveLeft(GameHistory history) {

            if (!finishedAnimation) {
                return false;
            }
        
        tempMap = copyMap();
        Location temp, temp2;
        boolean moved = false;
        Integer val;
        for (int i = 1; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                temp = new Location(i, j);
                if (moveTiles(Direction.LEFT, temp)) {
                    moved = true;
                }
            }
        }
        finishMove(moved, history);
        return moved;
    }

    public boolean moveRight(GameHistory history) {

            while (!finishedAnimation) {
            }
        
        tempMap = copyMap();
        Location temp, temp2;
        boolean moved = false;
        Integer val;
        for (int i = 2; i >= 0; i--) {
            for (int j = 0; j < 4; j++) {
                temp = new Location(i, j);
                if (moveTiles(Direction.RIGHT, temp)) {
                    moved = true;
                }
            }
        }
        finishMove(moved, history);
        return moved;
    }

    public boolean canPlay() {
        if (usedLoc.size() < BOARD_SIZE * BOARD_SIZE) {
            return true;
        }
        for (Entry<Location, Tile> entry : grid.entrySet()) {
            if (canMerge(entry.getKey(), Direction.UP, entry.getValue().getValue())) {
                return true;
            }
            if (canMerge(entry.getKey(), Direction.DOWN, entry.getValue().getValue())) {
                return true;
            }
            if (canMerge(entry.getKey(), Direction.LEFT, entry.getValue().getValue())) {
                return true;
            }
            if (canMerge(entry.getKey(), Direction.RIGHT, entry.getValue().getValue())) {
                return true;
            }
        }
        return false;
    }

    public HashMap<Location, Tile> copyMap() {
        HashMap<Location, Tile> newMap = new HashMap<>();

        for (Entry<Location, Tile> entry : grid.entrySet()) {
            newMap.put(entry.getKey(), new Tile(entry.getValue()));
        }

        return newMap;
    }

    public void setGrid(Pair<HashMap<Location, Tile>, Integer> arg) {
        usedLoc.clear();
        HashMap<Location, Tile> oldMap = arg.getKey();
        score.setScore(arg.getValue());
        for (Entry<Location, Tile> entry : grid.entrySet()) {
            if (oldMap.get(entry.getKey()).getValue() != 0) {
                usedLoc.add(entry.getKey());
            }
            entry.getValue().updateTile(oldMap.get(entry.getKey()).getValue());
        }

    }
}
