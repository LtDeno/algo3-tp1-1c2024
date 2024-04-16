package edu.fiuba;

class Coordinates {

    private int xCoord;
    private int yCoord;

    Coordinates(int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    void setCoords(int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    int getxCoord() {
        return this.xCoord;
    }

    int getyCoord() {
        return this.yCoord;
    }

    void addCoords(Coordinates coordsToAdd) {
        this.xCoord += coordsToAdd.getxCoord();
        this.yCoord += coordsToAdd.getyCoord();
    }

    void normalizeCoords() {
        if (this.xCoord != 0) this.xCoord /= Math.abs(this.xCoord);
        if (this.yCoord != 0) this.yCoord /= Math.abs(this.yCoord);
    }

    boolean areCoordsEqual(Coordinates coords) {
        return ((this.xCoord == coords.getxCoord()) && (this.yCoord == this.getyCoord()));
    }
}