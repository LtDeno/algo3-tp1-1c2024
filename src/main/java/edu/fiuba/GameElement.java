package edu.fiuba;

abstract class GameElement {

    private final String name;
    private Coordinate coords;
    private final int dMove;

    GameElement(String name, Coordinate coords, int dMove) {
        this.name = name;
        this.coords = coords;
        this.dMove = dMove;
    }

    String getName() {
        return this.name;
    }

    Coordinate getCoords() {
        return this.coords;
    }

    abstract void move();

}
