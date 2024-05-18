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

    public void setCoords(Coordinates newCoords) {
        this.coords.setCoords(newCoords);
    }

    public void setCollided() {
        this.collided = true;
    }

    public boolean isCollided() {
        return this.collided;
    }

    public void setKiller(GameElement killer) {
        this.killer = killer;
    }

    public GameElement getKiller() {
        return this.killer;
    }

    abstract void moveInDirection(Coordinates direction, Grid grid);
}