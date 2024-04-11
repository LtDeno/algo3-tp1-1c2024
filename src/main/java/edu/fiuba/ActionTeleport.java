package edu.fiuba;

public class ActionTeleport implements Action {

    private final int x;
    private final int y;
    private final boolean safe;

    ActionTeleport(int x, int y, boolean safe) {
        this.x = x;
        this.y = y;
        this.safe = safe;
    }
    @Override
    public void apply(Player player) {
        player.teleport(x, y, safe);
    }
}
