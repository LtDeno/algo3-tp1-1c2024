package edu.fiuba;

class ActionTeleportSafely extends ActionTeleportRandomly {

    ActionTeleportSafely(GameElement element, Grid grid) {
        super(element, grid);
    }

    @Override
    public void actuate() {
        this.teleport();
    }

    private void teleport() {
        this.grid.repositionElementAndItsCoords(element, grid.getUnoccupiedValidCoords());
    }
}
