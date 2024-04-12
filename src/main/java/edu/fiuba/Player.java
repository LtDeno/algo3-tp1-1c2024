package edu.fiuba;

class Player extends GameElement {

    private int randomTeleportsLeft;
    private int safeTeleportsLeft;

    Player(String name, Coordinate coords, int dMove, boolean destructible, int initialnRandomTP, int initialnSafeTP) {
        super(name, coords, dMove, destructible);
        this.randomTeleportsLeft = initialnRandomTP;
        this.safeTeleportsLeft = initialnSafeTP;
    }

    @Override
    void move(int dx, int dy) {
        int movementX = dx != 0 ? (dx / Math.abs(dx)) * dMove : 0;
        int movementY = dy != 0 ? (dy / Math.abs(dy)) * dMove : 0;
        coords.setxCoord(coords.getxCoord() + movementX);
        coords.setyCoord(coords.getyCoord() + movementY);
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
