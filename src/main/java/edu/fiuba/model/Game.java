package edu.fiuba.model;

import edu.fiuba.configpackage.Configurator;
import edu.fiuba.configpackage.EConfig;
import edu.fiuba.Constants;
import java.util.ArrayList;

public class Game {

    private final Configurator config;
    private final Character character;
    private Grid grid;
    private final ArrayList<Object> enemiesCurrentConfig = new ArrayList<>();
    private boolean gameEnded = false;
    private boolean readyForLevelUp = false;
    private int level = 1;
    private int score = 0;

    public Game(Configurator c) {
        this.config = c;
        this.listEnemiesAndCurrentAmount();
        if (this.isGridSizeInvalid()) System.exit(-1);

        this.grid = new Grid(this.config.getnRow(), this.config.getnCol());
        this.character = this.createCharacterFromConfig();
        this.grid.addGameElement(this.character);
        this.createEnemiesIntoGrid();
    }

    public boolean hasGameEnded() {
        return this.gameEnded;
    }

    public void characterMove(Coordinates characterMoveDirection) {
        if (this.gameEnded) return;
        if (this.levelUp()) return;

        this.character.moveInDirection(characterMoveDirection, this.grid);
        this.moveEnemiesTowardsCharacter();
        this.attemptToLevelUp();
    }

    public void characterTeleport() {
        if (this.gameEnded) return;
        if (this.levelUp()) return;
        if (!this.character.teleport(this.grid)) return;

        this.moveEnemiesTowardsCharacter();
        this.attemptToLevelUp();
    }

    public void characterTeleportSafely() {
        if (this.gameEnded) return;
        if (this.levelUp()) return;
        if (!this.character.teleportSafely(this.grid)) return;

        this.moveEnemiesTowardsCharacter();
        this.attemptToLevelUp();
    }

    boolean levelUp() {
        if (!readyForLevelUp) return false;
        this.readyForLevelUp = false;
        this.stepUpNumberOfEnemies();
        if (this.isGridSizeInvalid()) System.exit(-1);

        this.grid = new Grid(this.config.getnRow(), this.config.getnCol());

        this.character.setCoords(this.grid.getMiddleCoords());
        this.character.addRandomTP(this.config.getcConfig().getnStepRandomTP());
        this.character.addSafeTP(this.config.getcConfig().getnStepSafeTP());
        this.grid.addGameElement(this.character);

        this.createEnemiesIntoGrid();
        this.level++;

        return true;
    }

    private Character createCharacterFromConfig() {
       return new Character(
               Constants.CHARACTERNAME,
               this.grid.getMiddleCoords(),
               this.config.getcConfig().getdCharacterMove(),
               this.config.getcConfig().getnRandomTP(),
               this.config.getcConfig().getnSafeTP()
       );
    }

    private void createEnemiesIntoGrid() {
        for (int i = 0; i < this.enemiesCurrentConfig.size(); i+=2){
            for (int j = 0; j < ((int) this.enemiesCurrentConfig.get(i+1)); j++){
                this.grid.addGameElement(new Enemy(
                        ((EConfig) this.enemiesCurrentConfig.get(i)).getName(),
                        this.grid.getUnoccupiedValidCoords(),
                        ((EConfig) this.enemiesCurrentConfig.get(i)).getdEnemyMove()
                ));
            }
        }
    }

    private void moveEnemiesTowardsCharacter() {
        this.grid.getGameElements().forEach(gameElement -> {
            if ( !(gameElement instanceof Character) && !gameElement.isCollided() ) gameElement.moveInDirection(this.character.getCoords(), this.grid);
        });

        this.grid.reviseChangedElements();
        this.checkCollisions();
    }

    private void checkCollisions() {
        ArrayList<GameElement> collidedElements = this.grid.getCollidedElements();

        EConfig fueguitoConfig = null;
        for (EConfig eConfig : this.config.geteConfigs()) {
            if (eConfig.getName().equalsIgnoreCase(Constants.FIRENAME)) fueguitoConfig = eConfig;
        }

        for (GameElement gameElement : collidedElements) {
            if (gameElement instanceof Character) {
                this.grid.addGameElement(gameElement.getKiller());
                this.gameEnded = true;
                break;
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
            if (!(gameElement instanceof Character || gameElement.getName().equalsIgnoreCase(Constants.FIRENAME))) {
                successful = false;
                break;
            }
        }

        this.readyForLevelUp = successful;
    }

    private void listEnemiesAndCurrentAmount() {
        for (EConfig eConfig : this.config.geteConfigs()) {
            this.enemiesCurrentConfig.add(eConfig);
            this.enemiesCurrentConfig.add(eConfig.getnEnemy());
        }
    }

    private void stepUpNumberOfEnemies() {
        for (int i = 0; i < this.enemiesCurrentConfig.size(); i+=2) this.enemiesCurrentConfig.set(i + 1, (int) this.enemiesCurrentConfig.get(i + 1) + ((EConfig) this.enemiesCurrentConfig.get(i)).getnStepEnemy());
    }

    private boolean isGridSizeInvalid() {
        int cellCount = this.config.getnCol() * this.config.getnRow();
        int elementsCount = 1;
        for (int i = 0; i < this.enemiesCurrentConfig.size(); i+=2) elementsCount += ((int) this.enemiesCurrentConfig.get(i+1));

        return cellCount < elementsCount;
    }

    public Character getCharacter() {
        return this.character;
    }

    public Grid getGrid() {
        return this.grid;
    }

    public boolean isReadyForLevelUp() {
        return this.readyForLevelUp;
    }

    public int getLevel() {
        return this.level;
    }

    public int getScore() {
        return this.score;
    }
}
