package edu.fiuba;

class ActionTeleportRandomly implements Action {

    protected final GameElement e;
    protected final Grid grid;

    ActionTeleportRandomly(GameElement e, Grid grid) {
        this.e = e;
        this.grid = grid;
    }

    @Override
    public void actuate() {
        this.teleport();
    }

    private void teleport() {
        e.setCoords(grid.getRandomValidCoords());
    }
}
