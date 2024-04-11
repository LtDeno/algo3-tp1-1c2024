package edu.fiuba;



public class Enemy extends GameElement {

    private final int amount;

    Enemy(String name, Coordinate coords, int dMove, boolean destructible, int amountOf) {
        super(name, coords, dMove, destructible);
        this.amount = amountOf;
    }

    @Override
    void move(Grid grid) {
        /* no me dio la matematica para hacer el movimiento ni para entender la forma en la que lo habias hecho vos
        for (int i = 0; i < dMove; i++) {
            int movementX = dx != 0 ? dx / Math.abs(dx) : 0;
            int movementY = dy != 0 ? dy / Math.abs(dy) : 0;
            coords.setyCoord(coords.getyCoord() + movementY);
            coords.setxCoord(coords.getxCoord() + movementX);
        }
        */
    }

    int getAmount(){
        return this.amount;
    }
}
