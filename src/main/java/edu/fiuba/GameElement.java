package edu.fiuba;

abstract class GameElement {

    protected final String name;
    protected final Coordinates coords;
    protected final int dMove;
    protected final boolean destructible;
    protected boolean collided = false;

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
        this.coords.setCoords(newCoords);
    }

    void collide() {
        if (destructible) collided = true;
    }

    boolean getCollided() {
        return this.collided;
    }

    abstract void moveInDirection(Coordinates direction, Grid grid);

    void print() {
        System.out.printf("\n%s x=%d y=%d", this.name, this.coords.getxCoord(), this.coords.getyCoord());
    }
}