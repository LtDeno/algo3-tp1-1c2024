package edu.fiuba;

abstract class GameElement {

    protected final String name;
    protected final Coordinates coords;
    protected final int dMove;
    protected boolean collided = false;
    protected GameElement killer;

    GameElement(String name, Coordinates coords, int dMove) {
        this.name = name;
        this.coords = coords;
        this.dMove = dMove;
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

    void setCollided() {
        this.collided = true;
    }

    boolean isCollided() {
        return this.collided;
    }

    void setKiller(GameElement killer) {
        this.killer = killer;
    }

    GameElement getKiller() {
        return this.killer;
    }

    abstract void moveInDirection(Coordinates direction, Grid grid);

    void print() {
        System.out.printf("\n%s x=%d y=%d", this.name, this.coords.getxCoord(), this.coords.getyCoord());
    }
}