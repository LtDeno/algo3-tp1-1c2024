package edu.configpackage;

public class PConfig {
    private final int nRandomTP;
    private final int nStepRTP;
    private final int nSafeTP;
    private final int nStepSTP;
    private final int dPlayerMove;

    public PConfig(int nRandomTP, int nStepRTP, int nSafeTP, int nStepSTP, int dPlayerMove) {
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
        return nStepRTP;
    }

    public int getnSafeTP() {
        return nSafeTP;
    }

    public int getnStepSTP() {
        return nStepSTP;
    }

    public int getdPlayerMove() {
        return dPlayerMove;
    }
}
