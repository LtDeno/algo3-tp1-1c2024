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
        Coordinates finalPosition = this.vectorToMove.getAsSum(this.element.getCoords());
        if (this.grid.areCoordsInsideGrid(finalPosition)) {
            this.grid.repositionElement(element, finalPosition);
        }
    }
}
