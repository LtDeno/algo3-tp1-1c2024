package edu.fiuba.model;

import java.util.ArrayList;
import java.util.HashMap;

class Grid {

    private final int nColumns;
    private final int nRows;
    private final HashMap<String, GameElement> gameElements = new HashMap<>();
    private final ArrayList<GameElement> elementsToReposition = new ArrayList<>();
    private final ArrayList<GameElement> collidedElements = new ArrayList<>();

    protected Grid(int nRows, int nColumns) {
        this.nRows = nRows;
        this.nColumns = nColumns;
    }

    protected int getnColumns() {
        return this.nColumns;
    }

    protected int getnRows() {
        return this.nRows;
    }

    protected void addGameElement(GameElement element) {
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

    protected ArrayList<GameElement> getGameElements() {
        return new ArrayList<>(this.gameElements.values());
    }

    protected ArrayList<GameElement> getCollidedElements() {
        return this.collidedElements;
    }

    protected void clearCollidedElements() {
        this.collidedElements.clear();
    }

    protected Coordinates getMiddleCoords() {
        return new Coordinates((this.nColumns/2) + 1, (this.nRows/2) + 1);
    }

    protected Coordinates getRandomValidCoords() {
        Coordinates coords;
        do  {
            coords = new Coordinates((int) ((Math.random() * this.nColumns) + 1), (int) ((Math.random() * this.nRows) + 1));
        } while (!this.areCoordsInsideGrid(coords));

        return coords;
    }

    protected Coordinates getUnoccupiedValidCoords() {
        Coordinates coords;
        do {
            coords = this.getRandomValidCoords();
        } while (this.areCoordsOccupied(coords));

        return coords;
    }

    private boolean areCoordsOccupied(Coordinates coords) {
        return this.gameElements.containsKey(coords.getAsIndexFromMaxValues(this.nColumns, this.nRows));
    }

    protected boolean areCoordsInsideGrid(Coordinates coords) {
        return (coords.getxCoord() <= this.nColumns && coords.getxCoord() > 0 && coords.getyCoord() <= this.nRows && coords.getyCoord() > 0);
    }

    protected GameElement getElementAtCoordinates(Coordinates coords) {
        return this.gameElements.get(coords.getAsIndexFromMaxValues(this.nColumns, this.nRows));
    }

    protected void repositionElement(GameElement gameElement, Coordinates coords) {
        GameElement elementToReposition = this.gameElements.remove(gameElement.getCoords().getAsIndexFromMaxValues(this.nColumns, this.nRows));

        this.elementsToReposition.add(elementToReposition);
        elementToReposition.setCoords(coords);

        if (elementToReposition instanceof Character) {
            this.addGameElementWithCollision(elementToReposition);
            this.elementsToReposition.remove(this.elementsToReposition.size()-1);
        }
    }

    protected void reviseChangedElements() {
        for (GameElement gameElement : this.elementsToReposition) {
            this.addGameElementWithCollision(gameElement);
        }
        this.elementsToReposition.clear();
    }
}