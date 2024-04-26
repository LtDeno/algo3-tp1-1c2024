package edu.fiuba.model;

import edu.fiuba.Constants;

class Enemy extends GameElement {

    private final int scoreOnKill;

    Enemy(String name, Coordinates coords, int dMove, int scoreOnKill) {
        super(name, coords, dMove);
        this.scoreOnKill = scoreOnKill;
    }

    @Override
    void moveInDirection(Coordinates characterCoords, Grid grid) {
        if (this.coords.areCoordsEqual(characterCoords) || (this.dMove <= 0)) return;

        Coordinates finalMovement = Coordinates.ZERO;

        int i = 0;
        do {
            Coordinates expectedPosition = this.coords.getAsSum(finalMovement);
            if ((grid.getElementAtCoordinates(expectedPosition) != null) && (grid.getElementAtCoordinates(expectedPosition).getName().equalsIgnoreCase(Constants.FIRENAME))) break;

            Coordinates movementVector = new Coordinates(characterCoords.getxCoord() - expectedPosition.getxCoord(), characterCoords.getyCoord() - expectedPosition.getyCoord());
            movementVector.normalizeCoords();
            finalMovement = finalMovement.getAsSum(movementVector);

            i++;
        } while ((i < this.dMove) && !(grid.getElementAtCoordinates(finalMovement) instanceof Character));

        new ActionMove(this, finalMovement, grid).actuate();
    }

    int getScoreOnKill() {
        return this.scoreOnKill;
    }
}