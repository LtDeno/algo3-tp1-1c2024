package edu.fiuba;

import java.util.LinkedList;

class Grid {

    private final int nColumns;
    private final int nRows;
    private final LinkedList<GameElement> gameElements = new LinkedList<>();

    Grid(int nRows, int nColumns) {
        this.nRows = nRows;
        this.nColumns = nColumns;
    }

    void addGameElement(GameElement E) {
        this.gameElements.addLast(E);
    }

    int getnRows() {
        return this.nRows;
    }

    int getnColumns() {
        return this.nColumns;
    }

    Coordinate getMiddleCoords() {
        return new Coordinate(this.nColumns/2, this.nRows/2);
    }

    Coordinate getRandomValidCoords() {
        Coordinate coords;
        do  {
            coords = new Coordinate((int) (Math.random() * this.nColumns), (int) (Math.random() * this.nRows));
        } while (!this.areCoordsInsideGrid(coords));

        return coords;
    }

    Coordinate getUnoccupiedValidCoords() {
        Coordinate coords;
        do {
            coords = this.getRandomValidCoords();
        } while (this.areCoordsOccupied(coords));

        return coords;
    }

    boolean areCoordsOccupied(Coordinate coords) {
        boolean coordsAreOccupied = false;
        for (GameElement e : gameElements){
            coordsAreOccupied = (e.getCoords().getxCoord() == coords.getxCoord()) && (e.getCoords().getyCoord() == coords.getyCoord()) ;
        }

        return coordsAreOccupied;
    }

    boolean areCoordsInsideGrid(Coordinate coords) {
        return !(coords.getxCoord() >= nColumns || coords.getxCoord() < 0 || coords.getyCoord() >= nRows || coords.getyCoord() < 0);
    }
}
