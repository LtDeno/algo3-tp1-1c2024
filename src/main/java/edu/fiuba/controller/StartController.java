package edu.fiuba.controller;

import edu.fiuba.App;

import java.io.IOException;

public class StartController {
    public void switchToGame() throws IOException {
        App.changeSceneToGame();
    }
}
