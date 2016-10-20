/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hra2048;

import database.AbstractData;
import database.DerbyData;
import database.PlayerDAO;
import database.entities.Player;
import static hra2048.Hra2048.HISTORY_LENGTH;
import static hra2048.Hra2048.moves;
import static hra2048.Hra2048.score;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author dejdy
 */
public class GameManager {

    private Grid grid;
    private volatile boolean finished = true;
    private final BorderPane layout;
    private boolean gameOverShown = false;
    private final int GAME_WON_NUMBER = -1;
    private final GameHistory history;
    private int historyLevel = 0;
    private final Hra2048 application;

    public GameManager(Hra2048 application, BorderPane layout) {
        this.application = application;
        this.layout = layout;
        grid = new Grid(application);
        grid.createGrid(layout);
        history = new GameHistory();
    }

    private void animateGameEnd(Rectangle gameOverRect, Label gameOver) {
        FadeTransition ft = new FadeTransition();
        ft.setNode(gameOverRect);
        ft.setDuration(Duration.millis(350));
        ft.setFromValue(0.2);
        ft.setToValue(1);
        grid.gridStackPane.getChildren().add(gameOverRect);
        ft.play();
        ft.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                grid.gridStackPane.getChildren().add(gameOver);
                saveNewScore();
            }
        });
    }

    private void gameEnd(boolean gameWin) {
        if (gameOverShown) {
            return;
        }
        gameOverShown = true;
        Label gameOver;
        if (gameWin) {
            gameOver = new Label("You won!");
        } else {
            gameOver = new Label("Game Over!");
        }
        gameOver.setFont(Font.font("Century Gothic", FontWeight.BOLD, 50));
        Rectangle gameOverRect = new Rectangle(4 * Hra2048.squareSize + 15 * Hra2048.marginSize, 4 * Hra2048.squareSize + 15 * Hra2048.marginSize, Color.rgb(237, 194, 46, 0.5));
        gameOverRect.setArcHeight(10);
        gameOverRect.setArcWidth(10);

        animateGameEnd(gameOverRect, gameOver);
    }

    private boolean checkMoveValidity() {
        if (!finished || gameOverShown || !Grid.finishedAnimation || !Grid.finishedAnimation2) {
            return false;
        }

        return true;
    }

    private void checkGameEnd() {
        if (grid.getHighestTile() == GAME_WON_NUMBER) {
            gameEnd(true);
        }
        if (!grid.canPlay()) {
            gameEnd(false);
        }
    }

    private void newGame() {
        gameOverShown = false;
        historyLevel = 0;
        grid = new Grid(application);
        grid.createGrid(layout);
        history.clear();
        for (int i = 0; i < 2; i++) {
            grid.addRandomTile();
        }
        score.setScore(0);
        moves.setMoves(0);
    }

    private static AbstractData getDerbyData() {
        String url = ".";
        String username = "testovaci";
        String pass = "heslo";
        return new DerbyData(url, username, pass);
    }

    public void deleteHighScoreList() {

        AbstractData data = getDerbyData();
        PlayerDAO dao = data.getPlayerDAO();
        List<Player> res = new ArrayList<>();

        res = new ArrayList<>();
        res = (List<Player>) dao.getAll();

        for (Player p : res) {
            dao.deletePlayer(p.getId());
        }

    }

    private void setNewScoreGraphics(Stage scoreStage, GridPane gp, TextField field, Button saveButton, Button cancelButton) {
        scoreStage.setTitle("Save high score");

        saveButton.setText("Save");
        saveButton.setPadding(new Insets(10, 10, 10, 10));
        cancelButton.setText("Cancel");
        cancelButton.setPadding(new Insets(10, 10, 10, 10));
        Label nameLabel = new Label("Your name: ");
        nameLabel.setFont(Font.font("Century Gothic", 18));
        gp.add(nameLabel, 0, 0);
        gp.add(field, 0, 1);
        gp.add(saveButton, 1, 1);
        gp.add(cancelButton, 2, 1);

    }

    private void saveNewScore() {
        GridPane gp = new GridPane();
        gp.setHgap(10);
        gp.setPadding(new Insets(10, 10, 10, 10));
        TextField field = new TextField();
        Button saveButton = new Button();
        Button cancelButton = new Button();
        Stage scoreStage = new Stage();
        setNewScoreGraphics(scoreStage, gp, field, saveButton, cancelButton);
        scoreStage.setScene(new Scene(gp, 350, 80));
        scoreStage.show();

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                scoreStage.close();
            }
        });

        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                AbstractData data = getDerbyData();
                PlayerDAO dao = data.getPlayerDAO();
                dao.addPlayer(new Player(1, field.getText(), Hra2048.score.getScoreInt()));
                scoreStage.close();
            }
        });

    }

    private GridPane createScoreGrid() {
        GridPane gp = new GridPane();
        AbstractData data = getDerbyData();
        PlayerDAO dao = data.getPlayerDAO();
        List<Player> res = new ArrayList<>();
        res = (List<Player>) dao.getAll();
        Label rankLabel = new Label("Rank");
        Label nameLabel = new Label("Player's name");
        Label scoreLabel = new Label("Score");
        rankLabel.setPadding(new Insets(10, 10, 10, 10));
        rankLabel.setFont(Font.font("Century Gothic", FontWeight.BOLD, 22));
        nameLabel.setPadding(new Insets(10, 10, 10, 100));
        nameLabel.setFont(Font.font("Century Gothic", FontWeight.BOLD, 22));
        scoreLabel.setPadding(new Insets(10, 10, 10, 100));
        scoreLabel.setFont(Font.font("Century Gothic", FontWeight.BOLD, 22));
        gp.add(rankLabel, 0, 0);
        gp.add(nameLabel, 1, 0);
        gp.add(scoreLabel, 2, 0);
        for (int i = 0; i < res.size(); i++) {
            Label l0 = new Label(Integer.toString(i + 1));
            Label l1 = new Label(res.get(i).getName());
            Label l2 = new Label(Integer.toString(res.get(i).getHighScore()));
            l0.setPadding(new Insets(10, 10, 10, 10));
            l1.setPadding(new Insets(10, 10, 10, 100));
            l2.setPadding(new Insets(10, 10, 10, 100));
            l0.setFont(Font.font("Century Gothic", 20));
            l1.setFont(Font.font("Century Gothic", 20));
            l2.setFont(Font.font("Century Gothic", 20));
            gp.add(l0, 0, i + 1);
            gp.add(l1, 1, i + 1);
            gp.add(l2, 2, i + 1);
        }
        return gp;
    }

    private void showHighScoreList() {
        ScrollPane sp = new ScrollPane();
        GridPane gp = createScoreGrid();
        sp.setContent(gp);
        Stage scoreStage = new Stage();
        scoreStage.setTitle("High Score");
        scoreStage.setScene(new Scene(sp, 650, 450));
        scoreStage.show();
    }

    public void play(Scene scene) {

        for (int i = 0; i < 2; i++) {
            grid.addRandomTile();
        }

        scene.setOnKeyPressed((KeyEvent e) -> {
            switch (e.getCode()) {
                case DOWN:
                    if (!checkMoveValidity()) {
                        break;
                    }

                    finished = false;
                    historyLevel = 0;
                    if (grid.moveDown(history)) {
                        moves.inc();
                    }

                    checkGameEnd();

                    finished = true;
                    break;

                case UP:
                    if (!checkMoveValidity()) {
                        break;
                    }

                    finished = false;
                    historyLevel = 0;
                    if (grid.moveUp(history)) {
                        moves.inc();
                    }

                    checkGameEnd();

                    finished = true;
                    break;

                case LEFT:
                    if (!checkMoveValidity()) {
                        break;
                    }

                    finished = false;
                    historyLevel = 0;
                    if (grid.moveLeft(history)) {
                        moves.inc();
                    }

                    checkGameEnd();

                    finished = true;
                    break;

                case RIGHT:
                    if (!checkMoveValidity()) {
                        break;
                    }

                    finished = false;
                    historyLevel = 0;
                    if (grid.moveRight(history)) {
                        moves.inc();
                    }

                    checkGameEnd();

                    finished = true;
                    break;

                case Z:
                    if (moves.getMovesInt() > 0 && historyLevel < HISTORY_LENGTH && !gameOverShown && history.getSize() > 1) {
                        moves.dec();
                        historyLevel++;
                        if (historyLevel == 1) {
                            history.getPrev();
                        }
                        grid.setGrid(history.getPrev());
                    }
                    break;

                case R:
                    newGame();
                    break;

                case H:
                    showHighScoreList();
                    break;

                case D:
                    deleteHighScoreList();
                    break;
                    
                case ESCAPE:
                    System.exit(1);
                    break;

            }

        });

    }

}
