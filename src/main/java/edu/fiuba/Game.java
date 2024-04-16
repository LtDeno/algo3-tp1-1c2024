package edu.fiuba;

import edu.configpackage.Configurator;
import edu.configpackage.EConfig;

import java.util.ArrayList;

class Game {

    Configurator config;
    Grid grid;
    private final Player player;
    private final ArrayList<Object> enemiesCurrentConfig = new ArrayList<>();

    Game(Configurator c, String characterName) {
        this.config = c;
        this.grid = new Grid(this.config.getnRow(), this.config.getnCol());
        this.player = this.createPlayerFromConfig(characterName);
        this.grid.addGameElement(this.player);
        this.createEnemiesIntoGridAndListThem();
    }

    private Player createPlayerFromConfig(String characterName) {
       return new Player(characterName,
               this.grid.getMiddleCoords(),
               this.config.getcConfig().getdPlayerMove(),
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

    private void levelUp() {
        this.grid.addGameElement(this.player);
        this.player.addRandomTP(this.config.getcConfig().getnStepRandomTP());
        this.player.addSafeTP(this.config.getcConfig().getnStepSafeTP());
        this.createEnemiesIntoGridWithStep();
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
