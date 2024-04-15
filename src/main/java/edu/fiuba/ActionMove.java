package edu.fiuba;

class ActionMove implements Action{

    private final GameElement e;
    private final Grid grid;
    private final int dX;
    private final int dY;

    ActionMove(GameElement e, int dX, int dY, Grid grid) {
        this.e = e;
        this.grid = grid;
        this.dX = dX;
        this.dY = dY;
    }

    @Override
    public void actuate() {
        this.move();
    }

    private void move() {
        Coordinates newCoords = new Coordinates(this.e.getCoords().getxCoord() + this.dX, this.e.getCoords().getyCoord() + this.dY);
        if (this.grid.areCoordsInsideGrid(newCoords)) this.e.setCoords(newCoords);
    }
}
