package edu.fiuba.configpackage;

public class EConfig {

    private final String name;
    private final int nEnemy;
    private final int nStepEnemy;
    private final int dEnemyMove;
    private final int scoreOnKill;

    public EConfig(String name, int nEnemy, int nStepEnemy, int dEnemyMove, int scoreOnKill) {
        this.name = name;
        this.nEnemy = nEnemy;
        this.nStepEnemy = nStepEnemy;
        this.dEnemyMove = dEnemyMove;
        this.scoreOnKill = scoreOnKill;
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

    public int getScoreOnKill() {
        return this.scoreOnKill;
    }
}
