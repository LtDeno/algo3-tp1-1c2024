package edu.fiuba;

class ActionTeleportSafely extends ActionTeleportRandomly {

    ActionTeleportSafely(GameElement e, Grid grid) {
        super(e, grid);
    }

    @Override
    public void actuate() {
        this.teleport();
    }

    private void teleport() {
        this.e.setCoords(grid.getUnoccupiedValidCoords());
    }
}
