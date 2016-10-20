/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hra2048;

import static hra2048.Hra2048.squareSize;

/**
 *
 * @author dejdy
 */
public class Location {

    private final int x;
    private final int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Location(Location loc) {
        this.x = loc.getX();
        this.y = loc.getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Location offset(Direction direction) {
        return new Location(x + direction.getX(), y + direction.getY());
    }

    public boolean isValid() {
        if (x < 0 || y < 0 || x > 3 || y > 3) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.x;
        hash = 97 * hash + this.y;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Location other = (Location) obj;
        if (this.x != other.x) {
            return false;
        }
        return this.y == other.y;
    }

    public double getLayoutY() {
        if (y == 0) {
            return squareSize / 2;
        }
        return (y * squareSize) + squareSize;
    }

    public double getLayoutX() {
        if (x == 0) {
            return squareSize / 2;
        }
        return (x * squareSize) + squareSize;
    }

}
