package edu.fiuba;

@Deprecated
public class Fire extends Enemy {
    Fire(String name, Coordinate coords, int amountOf) {
        super(name, coords, 0, false, amountOf);
    }
}
