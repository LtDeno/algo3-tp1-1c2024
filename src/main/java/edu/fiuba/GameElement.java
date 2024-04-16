package edu.fiuba;

abstract class GameElement {

    protected final String name;
    protected Coordinates coords;
    protected final int dMove;
    protected final boolean destructible;


    GameElement(String name, Coordinates coords, int dMove, boolean destructible) {
        this.name = name;
        this.coords = coords;
        this.dMove = dMove;
        this.destructible = destructible;
    }

    String getName() {
        return this.name;
    }

    Coordinates getCoords() {
        return this.coords;
    }

    void setCoords(Coordinates newCoords) {
        this.coords = newCoords;
    }

    int getdMove() {
        return this.dMove;
    }

    boolean isDestructible() {
        return this.destructible;
    }

    abstract void moveInDirection(Coordinates coords, Grid grid);
}
