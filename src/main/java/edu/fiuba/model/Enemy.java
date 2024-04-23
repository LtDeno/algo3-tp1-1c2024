package edu.fiuba.model;

class Enemy extends GameElement {

    Enemy(String name, Coordinates coords, int dMove) {
        super(name, coords, dMove);
    }

    @Override
    void moveInDirection(Coordinates characterCoords, Grid grid) {
        if (this.coords.areCoordsEqual(characterCoords)) return;

        Coordinates finalMovement = Coordinates.ZERO;
        if (this.dMove <= 0) {
            new ActionMove(this, finalMovement, grid).actuate();
            return;
        }

        int i = 0;
        do {
            Coordinates expectedPosition = this.getCoords().getAsSum(finalMovement);
            Coordinates movementVector = new Coordinates(characterCoords.getxCoord() - expectedPosition.getxCoord(), characterCoords.getyCoord() - expectedPosition.getyCoord());
            movementVector.normalizeCoords();
            finalMovement = finalMovement.getAsSum(movementVector);
            i++;
        } while ((i < this.dMove) && !(grid.getElementAtCoordinates(finalMovement) instanceof Character));

        new ActionMove(this, finalMovement, grid).actuate();
    }
}