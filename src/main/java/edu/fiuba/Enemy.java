package edu.fiuba;

class Enemy extends GameElement {

    Enemy(String name, Coordinates coords, int dMove, boolean destructible) {
        super(name, coords, dMove, destructible);
    }

    @Override
    void moveInDirection(Coordinates playerCoords, Grid grid) {
        if (playerCoords.getxCoord() == this.coords.getxCoord() && playerCoords.getyCoord() == this.coords.getyCoord()) return;

        for (int i = 0; i < this.dMove; i++) {
            Coordinates movementVector = new Coordinates(playerCoords.getxCoord() - this.coords.getxCoord(), playerCoords.getyCoord() - this.coords.getyCoord());
            movementVector.normalizeCoords();

            new ActionMove(this, movementVector, grid).actuate();
        }
    }
}