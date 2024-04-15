package edu.fiuba;



public class Enemy extends GameElement {

    private final int amount;

    Enemy(String name, Coordinates coords, int dMove, boolean destructible, int amountOf) {
        super(name, coords, dMove, destructible);
        this.amount = amountOf;
    }

    @Override
    void move(int dx, int dy, Grid grid) {
        for (int i = 0; i < dMove; i++) {
            int movementX = dx != 0 ? dx / Math.abs(dx) : 0;
            int movementY = dy != 0 ? dy / Math.abs(dy) : 0;
            new ActionMove(this, movementX, movementY, grid);
        }
    }

    int getAmount(){
        return this.amount;
    }
}
