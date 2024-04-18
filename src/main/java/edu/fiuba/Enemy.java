package edu.fiuba;

class Enemy extends GameElement {

    Enemy(String name, Coordinates coords, int dMove) {
        super(name, coords, dMove);
    }

    @Override
    void moveInDirection(Coordinates characterCoords, Grid grid) {
        if (this.coords.areCoordsEqual(characterCoords)) return;

        for (int i = 0; i < this.dMove; i++) {
            Coordinates movementVector = new Coordinates(characterCoords.getxCoord() - this.coords.getxCoord(), characterCoords.getyCoord() - this.coords.getyCoord());
            movementVector.normalizeCoords();

            new ActionMove(this, movementVector, grid).actuate();
        }
    }
}