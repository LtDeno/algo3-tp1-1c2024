package edu.fiuba;

@Deprecated
public class FastRobot extends Enemy {
    FastRobot(String name, Coordinates coords, int amountOf) {
        super(name, coords, 2, true, amountOf);
    }
}
