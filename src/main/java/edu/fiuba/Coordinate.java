package edu.fiuba;

class Coordinate {

    private int xCoord;
    private int yCoord;

    Coordinate(int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    int getxCoord() {
        return this.xCoord;
    }

    int getyCoord() {
        return this.yCoord;
    }
}
