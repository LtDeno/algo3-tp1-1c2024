package edu.fiuba.model;

public class Coordinates {

    public static final Coordinates ZERO = new Coordinates(0, 0);
    private int xCoord;
    private int yCoord;

    public Coordinates(int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public void setCoords(Coordinates newCoords) {
        this.xCoord = newCoords.getxCoord();
        this.yCoord = newCoords.getyCoord();
    }

    public int getxCoord() {
        return this.xCoord;
    }

    public int getyCoord() {
        return this.yCoord;
    }

    public String getAsIndexFromMaxValues(int maxX, int maxY) {
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

    public Coordinates getAsSum(Coordinates coordsToAdd) {
        return new Coordinates(this.xCoord + coordsToAdd.getxCoord(), this.yCoord + coordsToAdd.getyCoord());
    }

    public void normalizeCoords() {
        if (this.xCoord != 0) this.xCoord /= Math.abs(this.xCoord);
        if (this.yCoord != 0) this.yCoord /= Math.abs(this.yCoord);
    }

    public boolean areCoordsEqual(Coordinates coords) {
        return (this.xCoord == coords.getxCoord() && this.yCoord == coords.getyCoord());
    }
}