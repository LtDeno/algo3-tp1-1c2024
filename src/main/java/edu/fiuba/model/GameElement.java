package edu.fiuba.model;

public abstract class GameElement {

    private final String name;
    protected final Coordinates coords;
    protected final int dMove;
    private boolean collided = false;
    private GameElement killer;

    protected GameElement(String name, Coordinates coords, int dMove) {
        this.name = name;
        this.coords = coords;
        this.dMove = dMove;
    }

    public String getName() {
        return this.name;
    }

    public Coordinates getCoords() {
        return this.coords;
    }

    protected void setCoords(Coordinates newCoords) {
        this.coords.setCoords(newCoords);
    }

    protected void setCollided() {
        this.collided = true;
    }

    protected boolean isCollided() {
        return this.collided;
    }

    protected void setKiller(GameElement killer) {
        this.killer = killer;
    }

    protected GameElement getKiller() {
        return this.killer;
    }

    abstract void moveInDirection(Coordinates direction, Grid grid);
}