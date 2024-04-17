package edu.fiuba;

class Coordinates {

    private int xCoord;
    private int yCoord;

    Coordinates(int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    void setCoords(Coordinates newCoords) {
        this.xCoord = newCoords.getxCoord();
        this.yCoord = newCoords.getyCoord();
    }

    int getxCoord() {
        return this.xCoord;
    }

    int getyCoord() {
        return this.yCoord;
    }

    String getAsIndexFromMaxValues(int maxX, int maxY) {
        String index = "";

        for (int i = 0; i < (String.valueOf(maxX).length() - String.valueOf(this.xCoord).length()); i++) {
            index = index.concat("0");
        }
        index = index.concat(String.valueOf(this.xCoord));

        for (int i = 0; i < (String.valueOf(maxY).length() - String.valueOf(this.yCoord).length()); i++) {
            index = index.concat("0");
        }
        index = index.concat(String.valueOf(this.yCoord));

        return index;
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