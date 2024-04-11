package edu.configpackage;

public class EConfig {

    private final String name;
    private final int nEnemy;
    private final int nStepEnemy;
    private final int dEnemyMove;

    EConfig(String name, int nEnemy, int nStepEnemy, int dEnemyMove) {
        this.name = name;
        this.nEnemy = nEnemy;
        this.nStepEnemy = nStepEnemy;
        this.dEnemyMove = dEnemyMove;
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
}
