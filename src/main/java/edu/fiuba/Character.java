package edu.fiuba;

class Character extends GameElement {

    private int randomTeleportsLeft;
    private int safeTeleportsLeft;

    Character(String name, Coordinates coords, int dMove, int initialnRandomTP, int initialnSafeTP) {
        super(name, coords, dMove);
        this.randomTeleportsLeft = initialnRandomTP;
        this.safeTeleportsLeft = initialnSafeTP;
    }

    @Override
    void moveInDirection(Coordinates movementVector, Grid grid) {
        if (movementVector.getxCoord() == 0 && movementVector.getyCoord() == 0) return;
        movementVector.normalizeCoords();

        for (int i = 1; i < this.dMove; i++) {
            movementVector = movementVector.getAsSum(movementVector);
        }

        new ActionMove(this, movementVector, grid).actuate();
    }

    void teleport(Grid grid) {
        if (randomTeleportsLeft > 0) {
            new ActionTeleportRandomly(this, grid).actuate();
            randomTeleportsLeft--;
        }
    }

    void teleportSafely(Grid grid) {
        if (safeTeleportsLeft > 0) {
            new ActionTeleportSafely(this, grid).actuate();
            safeTeleportsLeft--;
        }
    }

    void addRandomTP(int nToAdd) {
        this.randomTeleportsLeft += nToAdd;
    }

    void addSafeTP(int nToAdd) {
        this.safeTeleportsLeft += nToAdd;
    }
}
