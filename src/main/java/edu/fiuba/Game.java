package edu.fiuba;

import edu.configpackage.Configurator;
import edu.configpackage.EConfig;

import java.util.ArrayList;

class Game {

    private final Configurator config;
    private final Character character;
    private Grid grid;
    private final ArrayList<Object> enemiesCurrentConfig = new ArrayList<>();
    private boolean gameEnded = false;

    Game(Configurator c, String characterName) {
        this.config = c;
        this.character = this.createCharacterFromConfig(characterName);
        this.grid = new Grid(this.config.getnRow(), this.config.getnCol());
        this.grid.addGameElement(this.character);
        this.createEnemiesIntoGridAndListThem();
    }

    boolean isGameEnded() {
        return this.gameEnded;
    }

    void characterMove(Coordinates characterMoveDirection) {
        this.character.moveInDirection(characterMoveDirection, this.grid);
        this.grid.moveEnemies();
        this.gameEnded = this.characterGotHit();
    }

    void characterTeleport() {
        this.character.teleport(this.grid);
        this.grid.moveEnemies();
        this.gameEnded = this.characterGotHit();
    }

    void characterTeleportSafely() {
        this.character.teleportSafely(this.grid);
        this.grid.moveEnemies();
    }

    void levelUp() {
        this.grid = new Grid(this.config.getnRow(), this.config.getnCol());
        this.grid.addGameElement(this.character);
        this.character.addRandomTP(this.config.getcConfig().getnStepRandomTP());
        this.character.addSafeTP(this.config.getcConfig().getnStepSafeTP());
        this.createEnemiesIntoGridWithStep();
    }

    private Character createCharacterFromConfig(String characterName) {
       return new Character(characterName,
               this.grid.getMiddleCoords(),
               this.config.getcConfig().getdCharacterMove(),
               this.config.getcConfig().isDestructible(),
               this.config.getcConfig().getnRandomTP(),
               this.config.getcConfig().getnSafeTP()
       );
    }

    private void createEnemiesIntoGridAndListThem() {
        for (EConfig eConfig : config.geteConfigs()) {
            this.enemiesCurrentConfig.add(eConfig);
            this.enemiesCurrentConfig.add(eConfig.getnEnemy());

            for (int i = 0; i < eConfig.getnEnemy(); i++) {
                this.grid.addGameElement(new Enemy(eConfig.getName(),
                        this.grid.getUnoccupiedValidCoords(),
                        eConfig.getdEnemyMove(),
                        eConfig.getDestructible()));
            }
        }
    }

    private boolean characterGotHit() {
        ArrayList<GameElement> gameElements = this.grid.getGameElements();
        boolean characterGotHit = false;
        for (int i = 1; i < gameElements.size(); i++) {
            if (this.character.getCoords().areCoordsEqual(gameElements.get(i).getCoords())) characterGotHit = true;
        }

        return characterGotHit;
    }

    private void createEnemiesIntoGridWithStep() {
        for (int i = 0; i < this.enemiesCurrentConfig.size(); i+=2) {
            this.enemiesCurrentConfig.set(i+1, (int) this.enemiesCurrentConfig.get(i+1) + ((EConfig) this.enemiesCurrentConfig.get(i)).getnEnemy());

            for (int j = 0; j < (int) this.enemiesCurrentConfig.get(i+1); j++) {
                this.grid.addGameElement(new Enemy(((EConfig) this.enemiesCurrentConfig.get(i)).getName(),
                        this.grid.getUnoccupiedValidCoords(),
                        ((EConfig) this.enemiesCurrentConfig.get(i)).getdEnemyMove(),
                        ((EConfig) this.enemiesCurrentConfig.get(i)).getDestructible()));
            }
        }
    }

}
