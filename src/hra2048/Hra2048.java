/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hra2048;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author dejdy
 */
public class Hra2048 extends Application {

    private Stage primaryStage;
    private final BorderPane layout = new BorderPane();
    public static int squareSize = 100;
    public static int marginSize = 4;
    public static int HISTORY_LENGTH = 10;
    public static Score score = new Score();
    public static Moves moves = new Moves();
    private GameManager game;
    private Scene scene;

    private Parent createContent() {
        layout.setPrefSize(700, 700);
        game = new GameManager(this, layout);

        return layout;
    }

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        primaryStage.setTitle("Honzíkova 2048!");
        scene = new Scene(createContent());
        primaryStage.setScene(scene);
        primaryStage.show();
        game.play(scene);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
