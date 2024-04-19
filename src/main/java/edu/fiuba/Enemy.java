package edu.fiuba;

class Enemy extends GameElement {

    Enemy(String name, Coordinates coords, int dMove) {
        super(name, coords, dMove);
    }

    @Override
    void moveInDirection(Coordinates characterCoords, Grid grid) {
        if (this.coords.areCoordsEqual(characterCoords)) return;

        Coordinates finalMovement = Coordinates.ZERO;
        for (int i = 0; i < this.dMove; i++) {
            Coordinates expectedPosition = this.getCoords().getAsSum(finalMovement);
            Coordinates movementVector = new Coordinates(characterCoords.getxCoord() - expectedPosition.getxCoord(), characterCoords.getyCoord() - expectedPosition.getyCoord());
            movementVector.normalizeCoords();
            finalMovement = finalMovement.getAsSum(movementVector);
        }

        new ActionMove(this, finalMovement, grid).actuate();
    }
}