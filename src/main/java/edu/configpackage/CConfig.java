package edu.configpackage;

public class CConfig {

    private final int nRandomTP;
    private final int nStepRTP;
    private final int nSafeTP;
    private final int nStepSTP;
    private final int dPlayerMove;

    CConfig(int nRandomTP, int nStepRTP, int nSafeTP, int nStepSTP, int dPlayerMove) {
        this.nRandomTP = nRandomTP;
        this.nStepRTP = nStepRTP;
        this.nSafeTP = nSafeTP;
        this.nStepSTP = nStepSTP;
        this.dPlayerMove = dPlayerMove;
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
}
