package edu.fiuba;

@Deprecated
public class SlowRobot extends Enemy {
    SlowRobot(String name, Coordinate coords) {
        super(name, coords, 1, true);
    }
}
