package edu.configpackage;

public class EConfig {
    private final int nEnemy;
    private final int nStepEnemy;
    private final int dEnemyMove;

    public EConfig(int nEnemy, int nStepEnemy, int dEnemyMove) {
        this.nEnemy = nEnemy;
        this.nStepEnemy = nStepEnemy;
        this.dEnemyMove = dEnemyMove;
    }

    public int getnEnemy() {
        return nEnemy;
    }

    public int getnStepEnemy() {
        return nStepEnemy;
    }

    public int getdEnemyMove() {
        return dEnemyMove;
    }
}
