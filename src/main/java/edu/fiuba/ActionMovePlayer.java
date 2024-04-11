package edu.fiuba;

public class ActionMovePlayer implements Action {

    private final int dx;
    private final int dy;

    ActionMovePlayer(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void apply(Player player) {
        player.move(dx, dy);
    }
}
