/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hra2048;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author dejdy
 */
public class Tile extends StackPane {

    private Text text;
    private Rectangle rect;
    private Integer value = 0;
    private static final Duration ANIMATION_MERGE_TIME = Duration.millis(100);
    private static final Duration ANIMATION_NEW_TIME = Duration.millis(120);

    public Tile() {
        setupGraphics();
    }

    public Tile(Tile t) {
        value = t.value;
        setupGraphics();
        updateTile(value);
    }

    Integer getValue() {
        return value;
    }

    public double getRectX() {
        return rect.getX();
    }

    public double getRectY() {
        return rect.getY();
    }

    private void setupGraphics() {
        text = new Text();
        text.setText("");
        text.setFont(Font.font("Helvetica Neue", FontWeight.BOLD, 35));
        this.rect = new Rectangle(Hra2048.squareSize, Hra2048.squareSize, Color.rgb(238, 228, 218, 0.35));
        rect.setArcWidth(6);
        rect.setArcHeight(6);
        getChildren().addAll(rect, text);
    }

    public void updateTile(Integer newVal) {
        value = newVal;
        if (value == 0) {
            text.setText("");
        } else {
            text.setText(value.toString());
        }

        if (value >= 8) {
            text.setFill(Color.rgb(249, 246, 242));
        } else {
            text.setFill(Color.rgb(119, 110, 101));
        }

        if (value <= 512) {
            text.setFont(Font.font("Helvetica Neue", FontWeight.BOLD, 35));
        }
        if (value > 512) {
            text.setFont(Font.font("Helvetica Neue", FontWeight.BOLD, 30));
        }
        if (value > 8192) {
            text.setFont(Font.font("Helvetica Neue", FontWeight.BOLD, 25));
        }

        switch (value) {
            case 0:
                rect.setFill(Color.rgb(238, 228, 218, 0.35));
                break;

            case 2:
                rect.setFill(Color.rgb(238, 228, 218));
                break;

            case 4:
                rect.setFill(Color.rgb(237, 224, 200));
                break;

            case 8:
                rect.setFill(Color.rgb(242, 177, 121));
                break;

            case 16:
                rect.setFill(Color.rgb(245, 149, 99));
                break;

            case 32:
                rect.setFill(Color.rgb(246, 124, 95));
                break;

            case 64:
                rect.setFill(Color.rgb(246, 94, 59));
                break;

            case 128:
                rect.setFill(Color.rgb(237, 207, 114));
                break;

            case 256:
                rect.setFill(Color.rgb(237, 204, 97));
                break;

            case 512:
                rect.setFill(Color.rgb(237, 200, 80));
                break;

            case 1024:
                rect.setFill(Color.rgb(237, 197, 63));
                break;

            default:
                rect.setFill(Color.rgb(237, 194, 46));
                break;
        }

        /*
         switch (value) {
         case 0:
         rect.setFill(Color.rgb(238, 228, 218, 0.35));
         break;

         case 2:
         rect.setFill(Color.web("#DCECFF"));
         break;

         case 4:
         rect.setFill(Color.web("#DDFEFF"));
         break;

         case 8:
         rect.setFill(Color.web("#DDEDFF"));
         break;

         case 16:
         rect.setFill(Color.web("#C7E0FF"));
         break;

         case 32:
         rect.setFill(Color.web("#C5DEFF"));
         break;

         case 64:
         rect.setFill(Color.web("#CAC6FF"));
         break;
         case 128:
         rect.setFill(Color.web("#C1B5F2"));
         break;

         case 256:
         rect.setFill(Color.web("#BCAFF2"));
         break;

         case 512:
         rect.setFill(Color.web("#81A7FF"));
         break;

         case 1024:
         rect.setFill(Color.web("#7AB0FF"));
         break;

         default:
         rect.setFill(Color.web("#806CFF"));
         break;
         }
         */
    }

    public ScaleTransition animateNewlyAddedTile() {
        final ScaleTransition scaleTransition = new ScaleTransition(ANIMATION_NEW_TIME, this);
        scaleTransition.setFromX(0.5);
        scaleTransition.setFromY(0.5);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);
        scaleTransition.setAutoReverse(true);
        scaleTransition.setInterpolator(Interpolator.EASE_OUT);

        return scaleTransition;
    }

    public ScaleTransition animateMerge() {

        ScaleTransition scaleTransition = new ScaleTransition(ANIMATION_MERGE_TIME, this);
        scaleTransition.setFromX(1.2);
        scaleTransition.setFromY(1.2);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);
        scaleTransition.setAutoReverse(true);
        scaleTransition.setInterpolator(Interpolator.EASE_OUT);

        return scaleTransition;
    }
}
