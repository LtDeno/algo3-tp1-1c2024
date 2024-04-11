package edu.fiuba;



public class Enemy extends GameElement {
    Enemy(String name, Coordinate coords, int dMove, boolean destructible) {
        super(name, coords, dMove, destructible);
    }

    @Override
    public void move(int dx, int dy) {
        for (int i = 0; i < dMove; i++) {
            int movementX = dx != 0 ? dx / Math.abs(dx) : 0;
            int movementY = dy != 0 ? dy / Math.abs(dy) : 0;
            coords.setyCoord(coords.getyCoord() + movementY);
            coords.setxCoord(coords.getxCoord() + movementX);
        }
    }
}
