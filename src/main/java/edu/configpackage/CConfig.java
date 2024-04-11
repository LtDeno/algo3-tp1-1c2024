package edu.configpackage;

public class CConfig {

    private final int nRandomTP;
    private final int nStepRTP;
    private final int nSafeTP;
    private final int nStepSTP;
    private final int dPlayerMove;
    private final boolean destructible;

    CConfig(int nRandomTP, int nStepRTP, int nSafeTP, int nStepSTP, int dPlayerMove, boolean destructible) {
        this.nRandomTP = nRandomTP;
        this.nStepRTP = nStepRTP;
        this.nSafeTP = nSafeTP;
        this.nStepSTP = nStepSTP;
        this.dPlayerMove = dPlayerMove;
        this.destructible = destructible;
    }

    public int getnRandomTP() {
        return nRandomTP;
    }

    public int getnStepRTP() {
        return this.nStepRTP;
    }

    public int getnSafeTP() {
        return this.nSafeTP;
    }

    public int getnStepSTP() {
        return this.nStepSTP;
    }

    public int getdPlayerMove() {
        return this.dPlayerMove;
    }

    public boolean isDestructible() {
        return this.destructible;
    }
}
