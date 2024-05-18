package edu.fiuba.model;

class ActionTeleportSafely implements Action {

    private final GameElement element;
    private final Grid grid;
    private final Coordinates selectedCell;

    public ActionTeleportSafely(GameElement element, Grid grid, Coordinates selectedCell) {
        this.element = element;
        this.grid = grid;
        this.selectedCell = selectedCell;
    }

    public void actuate() {
        this.teleport();
    }

    private void teleport() {
        this.grid.repositionElement(this.element, this.selectedCell);
    }
}
