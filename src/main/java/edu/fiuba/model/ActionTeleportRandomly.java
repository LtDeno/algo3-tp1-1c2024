package edu.fiuba.model;

class ActionTeleportRandomly implements Action {

    private final GameElement element;
    private final Grid grid;

    protected ActionTeleportRandomly(GameElement element, Grid grid) {
        this.element = element;
        this.grid = grid;
    }

    @Override
    public void actuate() {
        this.teleport();
    }

    private void teleport() {
        Coordinates newCoords = this.grid.getRandomValidCoords();
        this.grid.repositionElement(this.element, newCoords);
    }
}