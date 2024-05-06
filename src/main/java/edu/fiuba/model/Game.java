package edu.fiuba.model;

import edu.fiuba.configpackage.Configurator;
import edu.fiuba.configpackage.EConfig;
import edu.fiuba.Constants;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Game {

    private final Configurator config;
    private final Character character;
    private Grid grid;
    private final ArrayList<Object> enemiesCurrentConfig = new ArrayList<>();
    private boolean gameEnded = false;
    private boolean gridFilled = false;
    private boolean hasLeveledUp = false;
    private boolean hasMovedInLevel = false;
    private int level = 1;
    private int score = 0;

    public Game(Configurator c) {
        this.config = c;
        this.listEnemiesAndCurrentAmount();

        this.grid = new Grid(this.config.getnRow(), this.config.getnCol());
        this.character = this.createCharacterFromConfig();
        this.grid.addGameElement(this.character);
        this.createEnemiesIntoGrid();
        this.startLevelUpAttemptTimer();
    }

    public void characterMove(Coordinates characterMoveDirection) {
        if (this.gameEnded) return;
        this.character.moveInDirection(characterMoveDirection, this.grid);

        this.hasMovedInLevel = true;
        this.moveEnemiesTowardsCharacter();
    }

    public void characterTeleport() {
        if (this.gameEnded) return;
        if (!this.character.teleport(this.grid)) return;

        this.hasMovedInLevel = true;
        this.moveEnemiesTowardsCharacter();
    }

    public void characterTeleportSafely(Coordinates selectedCell) {
        if (this.gameEnded) return;
        if (!this.character.teleportSafely(this.grid, selectedCell)) return;

        this.hasMovedInLevel = true;
        this.moveEnemiesTowardsCharacter();
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
                        ((EConfig) this.enemiesCurrentConfig.get(i)).getdEnemyMove(),
                        ((EConfig) this.enemiesCurrentConfig.get(i)).getScoreOnKill()
                ));
            }
        }
    }

    private void moveEnemiesTowardsCharacter() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                grid.getGameElements().forEach(gameElement -> {
                    if ( !(gameElement instanceof Character) && !gameElement.isCollided() ) gameElement.moveInDirection(character.getCoords(), grid);
                });

                grid.reviseChangedElements();
                checkCollisions();

                timer.cancel();
            }
        }, 150);
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
            } else if (!(gameElement.getKiller() instanceof Character) && fueguitoConfig != null) {
                this.score += ((Enemy) gameElement).getScoreOnKill();
                this.grid.addGameElement(new Enemy(fueguitoConfig.getName(), gameElement.getCoords(), fueguitoConfig.getdEnemyMove(), fueguitoConfig.getScoreOnKill()));
            }

        }
        this.grid.clearCollidedElements();
    }

    private boolean isReadyForLevelUp() {
        boolean successful = true;
        for (GameElement gameElement : this.grid.getGameElements()) {
            if (!(gameElement instanceof Character || gameElement.getName().equalsIgnoreCase(Constants.FIRENAME))) {
                successful = false;
                break;
            }
        }

        return successful;
    }

    private void levelUp() {
        this.hasLeveledUp = true;
        this.stepUpNumberOfEnemies();
        if (this.isGridSizeInvalid()) {
            this.gridFilled = true;
            this.gameEnded = true;
        }

        this.grid = new Grid(this.config.getnRow(), this.config.getnCol());

        this.character.setCoords(this.grid.getMiddleCoords());
        this.character.addRandomTP(this.config.getcConfig().getnStepRandomTP());
        this.character.addSafeTP(this.config.getcConfig().getnStepSafeTP());
        this.grid.addGameElement(this.character);

        this.createEnemiesIntoGrid();
        this.level++;
        this.hasMovedInLevel = false;
    }

    private void startLevelUpAttemptTimer() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (isReadyForLevelUp()) {
                    levelUp();
                } else {
                    hasLeveledUp = false;
                }
            }
        }, 0, 145);
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

    public int getCharacterPosX() {
        return this.character.getCoords().getxCoord();
    }

    public int getCharacterPosY() {
        return this.character.getCoords().getyCoord();
    }

    public String getCharacterRandomTeleportLeft() {
        return this.character.getRandomTeleportsLeft();
    }

    public String getCharacterSafeTeleportLeft() {
        return this.character.getSafeTeleportsLeft();
    }

    public int getGameWidth() {
        return this.grid.getnColumns();
    }

    public int getGameHeight() {
        return this.grid.getnRows();
    }

    public ArrayList<GameElement> getGameElements() {
        return this.grid.getGameElements();
    }

    public boolean hasLeveledUp() {
        return (this.hasLeveledUp && !this.hasMovedInLevel);
    }

    public boolean isGameEnded() {
        return this.gameEnded;
    }

    public boolean isGridFilled() {
        return this.gridFilled;
    }

    public int getLevel() {
        return this.level;
    }

    public int getScore() {
        return this.score;
    }
}