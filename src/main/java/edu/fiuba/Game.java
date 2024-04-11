package edu.fiuba;

import edu.configpackage.Configurator;
import edu.configpackage.EConfig;

import java.util.LinkedList;

class Game {

    Configurator config;
    Grid grid;
    private final Player player;
    private final LinkedList<Enemy> enemies = new LinkedList<>();

    Game(Configurator c, String characterName) {
        this.config = c;
        this.grid = new Grid(this.config.getnRow(), this.config.getnCol());
        this.player = this.createPlayerFromConfig(characterName);
        this.listEnemiesFromConfig();

        this.fillGrid();
    }

    private void fillGrid() {
        this.grid.addGameElement(this.player);

        for (Enemy enemy : this.enemies) {
            for (int i = 0; i < enemy.getAmount(); i++) {
                this.grid.addGameElement(enemy);
            }
        }
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

    private void listEnemiesFromConfig() {
        for (EConfig eConfig : config.geteConfigs()) {
            this.enemies.addLast(new Enemy(eConfig.getName(), this.grid.getUnoccupiedValidCoords(), eConfig.getdEnemyMove(), eConfig.getDestructible(), eConfig.getnEnemy()));
        }
    }
}
