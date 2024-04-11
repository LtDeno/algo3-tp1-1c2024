package edu.fiuba;

abstract class GameElement {

    private final String name;
    protected Coordinate coords;
    protected final int dMove;
    private final boolean destructible;


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


    abstract void move(int dx, int dy);
}
