package edu.fiuba;

public class ActionTeleportSafely implements Action {

    private final Grid grid;

    ActionTeleportSafely(Grid grid) {
        this.grid = grid;
    }

    @Override
    public void apply(Player player) {
        do {
            var x = (int)(grid.getnColumns() * Math.random());
            var y = (int)(grid.getnRows() * Math.random());
            player.teleport(x, y, true);
        } while(grid.checkPlayerCollision());

    }
}
