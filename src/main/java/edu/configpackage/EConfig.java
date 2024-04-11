package edu.configpackage;

public class EConfig {

    private final String name;
    private final int nEnemy;
    private final int nStepEnemy;
    private final int dEnemyMove;
    private final boolean destructible;

    EConfig(String name, int nEnemy, int nStepEnemy, int dEnemyMove, boolean destructible) {
        this.name = name;
        this.nEnemy = nEnemy;
        this.nStepEnemy = nStepEnemy;
        this.dEnemyMove = dEnemyMove;
        this.destructible = destructible;
    }

    public String getName() {
        return this.name;
    }

    public int getnEnemy() {
        return this.nEnemy;
    }

    public int getnStepEnemy() {
        return this.nStepEnemy;
    }

    public int getdEnemyMove() {
        return this.dEnemyMove;
    }

    public boolean getDestructible() {
        return this.destructible;
    }
}
