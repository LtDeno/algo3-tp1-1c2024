package edu.configpackage;

public class CConfig {

    private final int nRandomTP;
    private final int nStepRTP;
    private final int nSafeTP;
    private final int nStepSTP;
    private final int dCharacterMove;
    private final boolean destructible;

    CConfig(int nRandomTP, int nStepRTP, int nSafeTP, int nStepSTP, int dCharacterMove, boolean destructible) {
        this.nRandomTP = nRandomTP;
        this.nStepRTP = nStepRTP;
        this.nSafeTP = nSafeTP;
        this.nStepSTP = nStepSTP;
        this.dCharacterMove = dCharacterMove;
        this.destructible = destructible;
    }

    public int getnRandomTP() {
        return nRandomTP;
    }

    public int getnStepRandomTP() {
        return this.nStepRTP;
    }

    public int getnSafeTP() {
        return this.nSafeTP;
    }

    public int getnStepSafeTP() {
        return this.nStepSTP;
    }

    public int getdCharacterMove() {
        return this.dCharacterMove;
    }

    public boolean isDestructible() {
        return this.destructible;
    }
}
