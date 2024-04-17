package edu.fiuba;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

class Grid {

    private final int nColumns;
    private final int nRows;
    private final HashMap<String, GameElement> gameElements = new HashMap<>();
    private final ArrayList<GameElement> collidedElements = new ArrayList<>();

    Grid(int nRows, int nColumns) {
        this.nRows = nRows;
        this.nColumns = nColumns;
    }

    void addGameElement(GameElement element) {
        this.gameElements.put(element.getCoords().getAsIndexFromMaxValues(this.nColumns, this.nRows), element);
    }

    private void addGameElementWithCollision(GameElement element) {
        GameElement collidee = this.gameElements.put(element.getCoords().getAsIndexFromMaxValues(this.nColumns, this.nRows), element);
        if (collidee != null){
            this.collidedElements.add(collidee);
            this.collidedElements.add(element);
        }
    }

    void removeGameElement(GameElement element) {
        this.gameElements.remove(element.getCoords().getAsIndexFromMaxValues(this.nColumns, this.nRows));
    }

    int getnRows() {
        return this.nRows;
    }

    int getnColumns() {
        return this.nColumns;
    }

    Collection<GameElement> getGameElements() {
        return this.gameElements.values();
    }

    ArrayList<GameElement> getCollidedElements() {
        return this.collidedElements;
    }

    void clearCollidedElements() {
        this.collidedElements.clear();
    }

    Coordinates getMiddleCoords() {
        return new Coordinates(this.nColumns/2, this.nRows/2);
    }

    Coordinates getRandomValidCoords() {
        Coordinates coords;
        do  {
            coords = new Coordinates((int) (Math.random() * this.nColumns), (int) (Math.random() * this.nRows));
        } while (!this.areCoordsInsideGrid(coords));

        return coords;
    }

    Coordinates getUnoccupiedValidCoords() {
        Coordinates coords;
        do {
            coords = this.getRandomValidCoords();
        } while (this.areCoordsOccupied(coords));

        return coords;
    }

    boolean areCoordsOccupied(Coordinates coords) {
        return this.gameElements.containsKey(coords.getAsIndexFromMaxValues(this.nColumns, this.nRows));
    }

    boolean areCoordsInsideGrid(Coordinates coords) {
        return !(coords.getxCoord() >= this.nColumns || coords.getxCoord() < 0 || coords.getyCoord() >= this.nRows || coords.getyCoord() < 0);
    }

    void repositionElementAndItsCoords(GameElement element, Coordinates coords) {
        this.removeGameElement(element);
        element.setCoords(coords);
        this.addGameElementWithCollision(element);
    }
}