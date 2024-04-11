package edu.fiuba;

public class Player extends GameElement {

    private int safeTeleportsLeft;

    Player(String name, Coordinate coords, int dMove) {
        super(name, coords, dMove, true);

    }
    @Override
    public void move(int dx, int dy) {
        dx = dx/(Math.abs(dx)/dMove);
        dy = dy/(Math.abs(dy)/dMove);
        coords.setxCoord(coords.getxCoord() + dx);
        coords.setyCoord(coords.getyCoord() + dy);
    }


    public void teleport(int x, int y, boolean safe) {
        if (safe) {
            if (safeTeleportsLeft == 0) {
                return;
            }
            safeTeleportsLeft--;
        }
        coords.setxCoord(x);
        coords.setyCoord(y);
    }


}
