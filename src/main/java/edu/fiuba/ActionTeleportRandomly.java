package edu.fiuba;

class ActionTeleportRandomly implements Action {

    protected final GameElement element;
    protected final Grid grid;

    ActionTeleportRandomly(GameElement element, Grid grid) {
        this.element = element;
        this.grid = grid;
    }

    @Override
    public void actuate() {
        this.teleport();
    }

    private void teleport() {
        Coordinates newCoords = this.grid.getRandomValidCoords();
        this.grid.repositionElementAndItsCoords(this.element, newCoords);
    }
}