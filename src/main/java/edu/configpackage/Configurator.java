package edu.configpackage;

import java.io.*;
import java.util.*;

import com.google.gson.*;

public class Configurator {

    private final String fileName;
    private int nRow;
    private int nCol;
    private CConfig cConfig;
    private final LinkedList<EConfig> eConfigs = new LinkedList<EConfig>();

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

        } catch (IOException e) {
            this.createDefaultConfigFile();
            System.out.printf("\nConfig file could not be opened \nCause: %s", e.getCause());
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
        System.out.println(enemiesConfig);
        for (String k : enemiesKeys) {
            JsonObject enemy = enemiesConfig.getAsJsonObject(k);
            System.out.println("\n" + k);
            System.out.printf("%d %d %d", enemy.get("startingAmount").getAsInt(), enemy.get("addOnLevelUpAmount").getAsInt(), enemy.get("moveDistance").getAsInt());
            list.addLast(new EConfig(k,
                    enemy.get("startingAmount").getAsInt(),
                    enemy.get("addOnLevelUpAmount").getAsInt(),
                    enemy.get("moveDistance").getAsInt()
            ));
        }
    }

    private void createDefaultConfigFile() {
        try (FileWriter fileWriter = new FileWriter(this.fileName)){
            JsonObject defaultConfig = new JsonObject();
            defaultConfig.add("game", this.createDefaultGameConfig());

            fileWriter.append("\n").append(String.valueOf(defaultConfig));

            fileWriter.close();
        } catch (IOException e) {
            System.out.println("\nCould not write config.json \nProcess terminated");
            System.exit(-1);
        }
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
        gridConfig.addProperty("height", 15);
        gridConfig.addProperty("width", 20);

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
        enemiesConfig.add("1x", this.createEnemyConfig(4, 2, 1));
        enemiesConfig.add("2x", this.createEnemyConfig(2, 1, 2));
        enemiesConfig.add("fueguito", this.createEnemyConfig(0, 0, 0));

        return enemiesConfig;
    }

    private JsonObject createEnemyConfig(int startingAmount, int addOnLevelUpAmount, int moveDistance) {
        JsonObject enemyConfig = new JsonObject();
        enemyConfig.addProperty("startingAmount", startingAmount);
        enemyConfig.addProperty("addOnLevelUpAmount", addOnLevelUpAmount);
        enemyConfig.addProperty("moveDistance", moveDistance);

        return enemyConfig;
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
