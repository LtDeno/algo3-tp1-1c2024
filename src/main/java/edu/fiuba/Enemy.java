package edu.fiuba;

class Enemy extends GameElement {

    Enemy(String name, Coordinates coords, int dMove, boolean destructible) {
        super(name, coords, dMove, destructible);
    }
    /*
    @Override
    void moveInDirection(Coordinates characterCoords, Grid grid) {
        System.out.println(this.coords.areCoordsEqual(characterCoords));
        if (this.coords.areCoordsEqual(characterCoords)) return;

        for (int i = 0; i < this.dMove; i++) {
            Coordinates movementVector = new Coordinates(characterCoords.getxCoord() - this.coords.getxCoord(), characterCoords.getyCoord() - this.coords.getyCoord());
            movementVector.normalizeCoords();

            //new ActionMove(this, movementVector, grid).actuate();
            Coordinates newPosition = Coordinates.sumCoords(this.getCoords(), movementVector);
            if (grid.areCoordsInsideGrid(newPosition)) {
                grid.repositionElementAndItsCoords(this, newPosition);
            }
        }
    }
    */

    // el movimiento en vertical en la otra funcion no estaba andando bien, asi que preferi
    // reemplazarlo por la funcion que estaba antes pero refactorizada, igual despues podes
    // cambiarlo si queres
    @Override
    void moveInDirection(Coordinates vectorToMove, Grid grid) {
        var dx = vectorToMove.getxCoord();
        var dy = vectorToMove.getyCoord();
        for (int i = 0; i < dMove; i++) {
            int movementX = dx != 0 ? dx / Math.abs(dx) : 0;
            int movementY = dy != 0 ? dy / Math.abs(dy) : 0;
            Coordinates newPosition = new Coordinates(coords.getxCoord() + movementX, coords.getyCoord() + movementY);
            if (grid.areCoordsInsideGrid(newPosition)) {
                grid.repositionElementAndItsCoords(this, newPosition);
            }
            dx -= movementX;
            dy -= movementY;
        }
    }

}