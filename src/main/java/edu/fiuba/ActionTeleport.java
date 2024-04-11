package edu.fiuba;

public class ActionTeleport implements Action {

    private final Grid grid;

    ActionTeleport(Grid grid) {
        this.grid = grid;
    }
    @Override
    public void apply(Player player) {
        var x = (int)(grid.getnColumns() * Math.random());
        var y = (int)(grid.getnRows() * Math.random());
        player.teleport(x, y, false);
    }
}
