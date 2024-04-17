package edu.fiuba;

class ActionMove implements Action{

    private final GameElement element;
    private final Grid grid;
    private final Coordinates vectorToMove;

    ActionMove(GameElement element, Coordinates vectorToMove, Grid grid) {
        this.element = element;
        this.grid = grid;
        this.vectorToMove = vectorToMove;
    }

    @Override
    public void actuate() {
        this.move();
    }

    private void move() {
        this.vectorToMove.addCoords(this.element.getCoords());
        if (this.grid.areCoordsInsideGrid(this.vectorToMove)) {
            this.grid.repositionElementAndItsCoords(element, this.vectorToMove);
        }
    }
}
