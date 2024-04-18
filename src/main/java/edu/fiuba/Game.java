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
        this.grid = new Grid(this.config.getnRow(), this.config.getnCol());
        this.character = this.createCharacterFromConfig(characterName);
        this.grid.addGameElement(this.character);
        this.createEnemiesIntoGridAndListThem();
    }

    boolean isGameEnded() {
        return this.gameEnded;
    }

    void characterMove(Coordinates characterMoveDirection) {
        this.character.moveInDirection(characterMoveDirection, this.grid);
        this.moveEnemiesTowardsCharacter();
    }

    void characterTeleport() {
        this.character.teleport(this.grid);
        this.moveEnemiesTowardsCharacter();
    }

    void characterTeleportSafely() {
        this.character.teleportSafely(this.grid);
        this.moveEnemiesTowardsCharacter();
    }

    void levelUp() {
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
                this.grid.addGameElement(new Enemy(
                        eConfig.getName(),
                        this.grid.getUnoccupiedValidCoords(),
                        eConfig.getdEnemyMove(),
                        eConfig.getDestructible()));
            }
        }
    }

    private void moveEnemiesTowardsCharacter() {
        this.grid.getGameElements().forEach(gameElement -> {
            if (!gameElement.equals(this.character)) gameElement.moveInDirection(this.character.getCoords(), this.grid);
        });

        this.checkCollisions();
    }

    private void checkCollisions() {
        ArrayList<GameElement> collidedElements = this.grid.getCollidedElements();

        EConfig fueguitoConfig = null;
        for (EConfig eConfig : this.config.geteConfigs()) {
            if (eConfig.getName().equalsIgnoreCase("fueguito")) fueguitoConfig = eConfig;
        }

        for (GameElement element : collidedElements) {
            element.collide();
            if (!element.equals(this.character) && fueguitoConfig != null) this.grid.addGameElement(new Enemy(
                    fueguitoConfig.getName(),
                    element.getCoords(),
                    fueguitoConfig.getdEnemyMove(),
                    fueguitoConfig.getDestructible()));
        }
        this.grid.clearCollidedElements();

        this.gameEnded = this.character.getCollided();
    }

    private void createEnemiesIntoGridWithStep() {
        for (int i = 0; i < this.enemiesCurrentConfig.size(); i+=2) {
            this.enemiesCurrentConfig.set(i+1, (int) this.enemiesCurrentConfig.get(i+1) + ((EConfig) this.enemiesCurrentConfig.get(i)).getnStepEnemy());

            for (int j = 0; j < (int) this.enemiesCurrentConfig.get(i+1); j++) {
                this.grid.addGameElement(new Enemy(
                        ((EConfig) this.enemiesCurrentConfig.get(i)).getName(),
                        this.grid.getUnoccupiedValidCoords(),
                        ((EConfig) this.enemiesCurrentConfig.get(i)).getdEnemyMove(),
                        ((EConfig) this.enemiesCurrentConfig.get(i)).getDestructible()));
            }
        }
    }

    Character getCharacter(){ return this.character; }

    Grid getGrid() { return this.grid; }

    void printGameElements() {
        this.grid.getGameElements().forEach(e -> {
            System.out.printf("\n%s x=%d y=%d", e.getName(), e.getCoords().getxCoord(), e.getCoords().getyCoord());
        });
    }
}
