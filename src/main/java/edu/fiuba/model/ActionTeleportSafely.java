package edu.fiuba.model;

class ActionTeleportSafely extends ActionTeleportRandomly {

    ActionTeleportSafely(GameElement element, Grid grid) {
        super(element, grid);
    }

    @Override
    public void actuate() {
        this.teleport();
    }

    private void teleport() {
        Coordinates newCoords = this.grid.getUnoccupiedValidCoords();
        this.grid.repositionElement(this.element, newCoords);
    }
}
