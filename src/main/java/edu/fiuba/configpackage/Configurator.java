package edu.fiuba.configpackage;

import java.io.*;
import java.util.*;
import com.google.gson.*;
import edu.fiuba.Constants;

public class Configurator {

    private final String fileName;
    private int nRow;
    private int nCol;
    private CConfig cConfig;
    private final LinkedList<EConfig> eConfigs = new LinkedList<>();

    public Configurator(String configFileName) {
        this.fileName = configFileName;
        this.readConfigFile();
    }

    private void readConfigFile() {
        try (FileReader fileReader = new FileReader(this.fileName)) {
            JsonObject fileConfig = new JsonStreamParser(fileReader).next().getAsJsonObject();

            this.nRow = fileConfig.getAsJsonObject("game").getAsJsonObject("grid").get("height").getAsInt();
            this.nCol = fileConfig.getAsJsonObject("game").getAsJsonObject("grid").get("width").getAsInt();

            JsonObject characterConfig = fileConfig.getAsJsonObject("game").getAsJsonObject("character");
            this.cConfig = this.createCConfig(characterConfig);

            JsonObject enemiesConfig =  fileConfig.getAsJsonObject("game").getAsJsonObject("enemies");
            this.addEnemiesToList(enemiesConfig, this.eConfigs);

            System.out.println("\nSuccessfully loaded 'config.json'.");
        } catch (IOException e) {
            if (this.createDefaultConfigFile()) {
                System.out.println("\nSuccessfully created default 'config.json'.");
                this.readConfigFile();
            } else {
                this.readWriteError(e);
            }
        }
    }

    private CConfig createCConfig(JsonObject characterConfig) {
        return new CConfig(
                characterConfig.get("startingRandomTPAmount").getAsInt(),
                characterConfig.get("addRandomTPOnLevelUpAmount").getAsInt(),
                characterConfig.get("startingSafeTPAmount").getAsInt(),
                characterConfig.get("addSafeTPOnLevelUpAmount").getAsInt(),
                characterConfig.get("moveDistance").getAsInt()
        );
    }

    private void addEnemiesToList(JsonObject enemiesConfig, LinkedList<EConfig> list) {
        String[] enemiesKeys = enemiesConfig.keySet().toArray(new String[0]);

        for (String k : enemiesKeys) {
            JsonObject enemy = enemiesConfig.getAsJsonObject(k);
            list.addLast(new EConfig(k,
                    enemy.get("startingAmount").getAsInt(),
                    enemy.get("addOnLevelUpAmount").getAsInt(),
                    enemy.get("moveDistance").getAsInt(),
                    enemy.get("scoreOnKill").getAsInt()
            ));
        }
    }

    private boolean createDefaultConfigFile() {
        if (new File(this.fileName).isFile()) return false;

        try (FileWriter fileWriter = new FileWriter(this.fileName)){
            JsonObject defaultConfig = new JsonObject();
            defaultConfig.add("game", this.createDefaultGameConfig());

            fileWriter.append("\n").append(String.valueOf(defaultConfig));
        } catch (IOException e) {
            System.out.println("\nCould not write 'config.json'." +
                    "\nProcess will be terminated.");
            return false;
        }
        return true;
    }

    private JsonObject createDefaultGameConfig() {
        JsonObject gameConfig = new JsonObject();
        gameConfig.add("grid", this.createDefaultGridConfig());
        gameConfig.add("character", this.createDefaultCharacterConfig());
        gameConfig.add("enemies", this.createDefaultEnemiesConfig());

        return gameConfig;
    }

    private JsonObject createDefaultGridConfig() {
        JsonObject gridConfig = new JsonObject();
        gridConfig.addProperty("height", 20);
        gridConfig.addProperty("width", 25);

        return gridConfig;
    }

    private JsonObject createDefaultCharacterConfig() {
        JsonObject characterConfig = new JsonObject();
        characterConfig.addProperty("startingRandomTPAmount", -1);
        characterConfig.addProperty("addRandomTPOnLevelUpAmount", 0);
        characterConfig.addProperty("startingSafeTPAmount", 1);
        characterConfig.addProperty("addSafeTPOnLevelUpAmount", 1);
        characterConfig.addProperty("moveDistance", 1);

        return characterConfig;
    }

    private JsonObject createDefaultEnemiesConfig() {
        JsonObject enemiesConfig = new JsonObject();
        enemiesConfig.add(Constants.SLOWROBOTNAME, this.createEnemyConfig(4, 2, 1, 1));
        enemiesConfig.add(Constants.FASTROBOTNAME, this.createEnemyConfig(2, 1, 2, 2));
        enemiesConfig.add(Constants.FIRENAME, this.createEnemyConfig(0, 0, 0, 0));

        return enemiesConfig;
    }

    private JsonObject createEnemyConfig(int startingAmount, int addOnLevelUpAmount, int moveDistance, int scoreOnKill) {
        JsonObject enemyConfig = new JsonObject();
        enemyConfig.addProperty("startingAmount", startingAmount);
        enemyConfig.addProperty("addOnLevelUpAmount", addOnLevelUpAmount);
        enemyConfig.addProperty("moveDistance", moveDistance);
        enemyConfig.addProperty("scoreOnKill", scoreOnKill);

        return enemyConfig;
    }

    private void readWriteError(IOException e) {
        System.out.printf("\nConfig file could not be opened, read or written." +
                "\nCause: %s." +
                "\nIf the cause is unknown, please relaunch the app." +
                "\nIf error continues please proceed to deleting file 'config.json'.", e.getCause());
        System.exit(-1);
    }

    public int getnRow() {
        return this.nRow;
    }

    public int getnCol() {
        return this.nCol;
    }

    public CConfig getcConfig() {
        return this.cConfig;
    }

    public LinkedList<EConfig> geteConfigs() {
        return this.eConfigs;
    }
}