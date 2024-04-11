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
    void move(Grid grid) {
        /* no me dio la matematica para hacer el movimiento ni para entender la forma en la que lo habias hecho vos
        dx = dx/(Math.abs(dx)/dMove);
        dy = dy/(Math.abs(dy)/dMove);
        coords.setxCoord(coords.getxCoord() + dx);
        coords.setyCoord(coords.getyCoord() + dy);
        */
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
