package edu.fiuba;

class ActionMove implements Action{

    private final GameElement e;
    private final Grid grid;
    private final Coordinates vectorToMove;

    ActionMove(GameElement e, Coordinates vectorToMove, Grid grid) {
        this.e = e;
        this.grid = grid;
        this.vectorToMove = vectorToMove;
    }

    @Override
    public void actuate() {
        this.move();
    }

    private void move() {
        this.vectorToMove.addCoords(this.e.getCoords());
        if (this.grid.areCoordsInsideGrid(this.vectorToMove)) this.e.setCoords(this.vectorToMove);
    }
}
