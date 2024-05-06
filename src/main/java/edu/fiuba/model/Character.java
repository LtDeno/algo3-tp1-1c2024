package edu.fiuba.model;

public class Character extends GameElement {

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

    boolean teleport(Grid grid) {
        if (this.randomTeleportsLeft != 0) {
            new ActionTeleportRandomly(this, grid).actuate();
            if (this.randomTeleportsLeft > 0) randomTeleportsLeft--;
            return true;
        }
        return false;
    }

    boolean teleportSafely(Grid grid, Coordinates selectedCell) {
        if (this.safeTeleportsLeft != 0) {
            new ActionTeleportSafely(this, grid, selectedCell).actuate();
            if (this.safeTeleportsLeft > 0) safeTeleportsLeft--;
            return true;
        }
        return false;
    }

    void addRandomTP(int nToAdd) {
        this.randomTeleportsLeft += nToAdd;
    }

    void addSafeTP(int nToAdd) {
        this.safeTeleportsLeft += nToAdd;
    }

    public String getRandomTeleportsLeft() {
        return (this.randomTeleportsLeft < 0 ? "infinite" : String.valueOf(this.randomTeleportsLeft));
    }

    public String getSafeTeleportsLeft() {
        return (this.safeTeleportsLeft < 0 ? "infinite" : String.valueOf(this.safeTeleportsLeft));
    }


}
