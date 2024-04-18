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
    private boolean readyForLevelUp = false;

    Game(Configurator c, String characterName) {
        this.config = c;
        this.grid = new Grid(this.config.getnRow(), this.config.getnCol());
        this.character = this.createCharacterFromConfig(characterName);
        this.grid.addGameElement(this.character);
        this.createEnemiesIntoGridAndListThem();
    }

    void characterMove(Coordinates characterMoveDirection) {
        if (this.gameEnded) return;

        this.character.moveInDirection(characterMoveDirection, this.grid);
        this.moveEnemiesTowardsCharacter();
        this.attemptToLevelUp();
    }

    void characterTeleport() {
        if (this.gameEnded) return;

        this.character.teleport(this.grid);
        this.moveEnemiesTowardsCharacter();
        this.attemptToLevelUp();
    }

    void characterTeleportSafely() {
        if (this.gameEnded) return;

        this.character.teleportSafely(this.grid);
        this.moveEnemiesTowardsCharacter();
        this.attemptToLevelUp();
    }

    void levelUp() {
        if (!readyForLevelUp) return;

        this.readyForLevelUp = false;
        this.grid = new Grid(this.config.getnRow(), this.config.getnCol());
        this.grid.addGameElement(this.character);
        this.character.addRandomTP(this.config.getcConfig().getnStepRandomTP());
        this.character.addSafeTP(this.config.getcConfig().getnStepSafeTP());
        this.createEnemiesIntoGridWithStep();
    }

    private Character createCharacterFromConfig(String characterName) {
       return new Character(
               characterName,
               this.grid.getMiddleCoords(),
               this.config.getcConfig().getdCharacterMove(),
               this.config.getcConfig().getnRandomTP(),
               this.config.getcConfig().getnSafeTP()
       );
    }

    private void createEnemiesIntoGridAndListThem() {
        for (EConfig eConfig : config.geteConfigs()) {
            this.enemiesCurrentConfig.add(eConfig);
            this.enemiesCurrentConfig.add(eConfig.getnEnemy());

            for (int i = 0; i < eConfig.getnEnemy(); i++) {
                this.grid.addGameElement(new Enemy(
                        eConfig.getName(),
                        this.grid.getUnoccupiedValidCoords(),
                        eConfig.getdEnemyMove()));
            }
        }
    }

    private void moveEnemiesTowardsCharacter() {
        this.grid.getGameElements().forEach(gameElement -> {
            if (!(gameElement instanceof Character)) gameElement.moveInDirection(this.character.getCoords(), this.grid);
        });

        this.checkCollisions();
    }

    private void checkCollisions() {
        ArrayList<GameElement> collidedElements = this.grid.getCollidedElements();

        EConfig fueguitoConfig = null;
        for (EConfig eConfig : this.config.geteConfigs()) {
            if (eConfig.getName().equalsIgnoreCase("fueguito")) fueguitoConfig = eConfig;
        }

        for (GameElement gameElement : collidedElements) {
            if (gameElement instanceof Character) {
                System.out.println("\nGG EZ");
                this.gameEnded = true;
            } else if (!(gameElement.getKiller() instanceof Character) && fueguitoConfig != null) this.grid.addGameElement(new Enemy(
                    fueguitoConfig.getName(),
                    gameElement.getCoords(),
                    fueguitoConfig.getdEnemyMove()));
        }
        this.grid.clearCollidedElements();
    }

    private void attemptToLevelUp() {
        boolean successful = true;
        for (GameElement gameElement : this.getGrid().getGameElements()) {
            if (!(gameElement instanceof Character || gameElement.getName().equalsIgnoreCase("fueguito"))) {
                successful = false;
                break;
            }
        }

        this.readyForLevelUp = successful;
    }

    private void createEnemiesIntoGridWithStep() {
        for (int i = 0; i < this.enemiesCurrentConfig.size(); i+=2) {
            this.enemiesCurrentConfig.set(i+1, (int) this.enemiesCurrentConfig.get(i+1) + ((EConfig) this.enemiesCurrentConfig.get(i)).getnStepEnemy());

            for (int j = 0; j < (int) this.enemiesCurrentConfig.get(i+1); j++) {
                this.grid.addGameElement(new Enemy(
                        ((EConfig) this.enemiesCurrentConfig.get(i)).getName(),
                        this.grid.getUnoccupiedValidCoords(),
                        ((EConfig) this.enemiesCurrentConfig.get(i)).getdEnemyMove()));
            }
        }
    }

    Character getCharacter(){ return this.character; }

    Grid getGrid() { return this.grid; }
}
