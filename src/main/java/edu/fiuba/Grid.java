package edu.fiuba;

import java.util.LinkedList;

class Grid {

    private final int nRows;
    private final int nColumns;
    private final LinkedList<GameElement> gameElements = new LinkedList<GameElement>();

    Grid(int nRows, int nColumns) {
        this.nRows = nRows;
        this.nColumns = nColumns;
    }

    void addGameElement(GameElement E) {
        this.gameElements.addLast(E);
    }
    boolean checkPlayerCollision() {
        return false;
    }

    public int getnRows() { return nRows; }
    public int getnColumns() { return nColumns; }

}
