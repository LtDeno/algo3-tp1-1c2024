package edu.fiuba;

abstract class GameElement {

    protected final String name;
    protected Coordinate coords;
    protected final int dMove;
    protected final boolean destructible;


    GameElement(String name, Coordinate coords, int dMove, boolean destructible) {
        this.name = name;
        this.coords = coords;
        this.dMove = dMove;
        this.destructible = destructible;
    }

    String getName() {
        return this.name;
    }

    Coordinate getCoords() {
        return this.coords;
    }

    void setCoords(Coordinate newCoords) {
        this.coords = newCoords;
    }

    int getdMove() {
        return this.dMove;
    }

    boolean isDestructible() {
        return this.destructible;
    }

    abstract void move(int dx, int dy);
}
