package edu.fiuba;

import java.util.ArrayList;
import java.util.HashMap;

class Grid {

    private final int nColumns;
    private final int nRows;
    private final HashMap<String, GameElement> gameElements = new HashMap<>();
    private final ArrayList<GameElement> elementsToReposition = new ArrayList<>();
    private final ArrayList<GameElement> collidedElements = new ArrayList<>();

    Grid(int nRows, int nColumns) {
        this.nRows = nRows;
        this.nColumns = nColumns;
    }

    int getnColumns() {
        return this.nColumns;
    }

    int getnRows() {
        return this.nRows;
    }

    void addGameElement(GameElement element) {
        this.gameElements.put(element.getCoords().getAsIndexFromMaxValues(this.nColumns, this.nRows), element);
    }

    private void addGameElementWithCollision(GameElement element) {
        GameElement collidee = this.gameElements.put(element.getCoords().getAsIndexFromMaxValues(this.nColumns, this.nRows), element);
        if (collidee != null){
            this.executeCollisionOfGameElement(collidee, element);
            this.executeCollisionOfGameElement(element, collidee);
        }
    }

    private void executeCollisionOfGameElement(GameElement gameElement, GameElement killer) {
        this.collidedElements.add(gameElement);
        gameElement.setCollided();
        gameElement.setKiller(killer);
    }

    ArrayList<GameElement> getGameElements() {
        ArrayList<GameElement> copiedElements = new ArrayList<>();
        this.gameElements.forEach((key, gameElement) -> copiedElements.add(gameElement));
        return copiedElements;
    }

    ArrayList<GameElement> getCollidedElements() {
        return this.collidedElements;
    }

    void clearCollidedElements() {
        this.collidedElements.clear();
    }

    Coordinates getMiddleCoords() {
        return new Coordinates(this.nColumns/2, this.nRows/2);
    }

    Coordinates getRandomValidCoords() {
        Coordinates coords;
        do  {
            coords = new Coordinates((int) (Math.random() * this.nColumns), (int) (Math.random() * this.nRows));
        } while (!this.areCoordsInsideGrid(coords));

        return coords;
    }

    Coordinates getUnoccupiedValidCoords() {
        Coordinates coords;
        do {
            coords = this.getRandomValidCoords();
        } while (this.areCoordsOccupied(coords));

        return coords;
    }

    GameElement getElementAtCoordinates(Coordinates coords) {
        return this.gameElements.get(coords.getAsIndexFromMaxValues(this.nColumns, this.nRows));
    }

    boolean areCoordsOccupied(Coordinates coords) {
        return this.gameElements.containsKey(coords.getAsIndexFromMaxValues(this.nColumns, this.nRows));
    }

    boolean areCoordsInsideGrid(Coordinates coords) {
        return (coords.getxCoord() <= this.nColumns && coords.getxCoord() > 0 && coords.getyCoord() <= this.nRows && coords.getyCoord() > 0);
    }

    void repositionElement(GameElement gameElement, Coordinates coords) {
        GameElement elementToReposition = this.gameElements.remove(gameElement.getCoords().getAsIndexFromMaxValues(this.nColumns, this.nRows));

        this.elementsToReposition.add(elementToReposition);
        elementToReposition.setCoords(coords);

        if (elementToReposition instanceof Character) {
            this.addGameElementWithCollision(elementToReposition);
            this.elementsToReposition.remove(this.elementsToReposition.size()-1);
        }
    }

    void reviseChangedElements() {
        for (GameElement gameElement : this.elementsToReposition) {
            this.addGameElementWithCollision(gameElement);
        }
        this.elementsToReposition.clear();
    }
}