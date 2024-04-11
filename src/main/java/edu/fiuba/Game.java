package edu.fiuba;

import edu.configpackage.Configurator;

class Game {

    Configurator config;
    Grid grid;

    Game(Configurator c){
        this.config = c;
        this.grid = new Grid(config.getnRow(), config.getnCol());
    }


}
