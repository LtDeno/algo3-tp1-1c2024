package edu.configpackage;

import java.io.File;
import java.util.LinkedList;

public class Configurator {

    // File file = new File("config.json");
    File file = new File("config.txt");
    private int nRow;
    private int nCol;
    private int timer;
    private CConfig cConfig;
    private final LinkedList<EConfig> eConfigs = new LinkedList<EConfig>();

    public Configurator() {
        this.readConfigFile();
    }

    private void readConfigFile() {
        //if (!file.exists()) this.createDefaultConfigFile();
        if (file.exists()) {
            System.out.println("Existe el archivo");
        } else {
            System.out.println("No existe el archivo");
        }

    }

    private void createDefaultConfigFile() {

    }

    public int getnRow() {
        return this.nRow;
    }

    public int getnCol() {
        return this.nCol;
    }

    public int getTimer() {
        return this.timer;
    }

    public CConfig getcConfig() {
        return this.cConfig;
    }

    public LinkedList<EConfig> geteConfigs() {
        return this.eConfigs;
    }

}
