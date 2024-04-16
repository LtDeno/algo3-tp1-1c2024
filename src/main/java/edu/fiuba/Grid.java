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
        boolean coordsAreOccupied = false;
        for (GameElement e : gameElements){
            coordsAreOccupied = (e.getCoords().getxCoord() == coords.getxCoord()) && (e.getCoords().getyCoord() == coords.getyCoord()) ;
        }

        return coordsAreOccupied;
    }

    boolean areCoordsInsideGrid(Coordinates coords) {
        return !(coords.getxCoord() >= nColumns || coords.getxCoord() < 0 || coords.getyCoord() >= nRows || coords.getyCoord() < 0);
    }

    void moveEnemies() {
        Coordinates playerCoords = this.gameElements.get(0).getCoords();

        for (int i = 1; i < gameElements.size(); i++) {
            this.gameElements.get(i).moveInDirection(playerCoords, this);
        }
    }
}
