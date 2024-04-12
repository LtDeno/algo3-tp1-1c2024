package edu.fiuba;

@Deprecated
public class SlowRobot extends Enemy {
    SlowRobot(String name, Coordinate coords, int amountOf) {
        super(name, coords, 1, true, amountOf);
    }
}
