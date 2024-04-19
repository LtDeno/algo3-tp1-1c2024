package edu.fiuba;

import javafx.event.ActionEvent;

import java.io.IOException;

public class StartController {
    public void switchToGame(ActionEvent actionEvent) throws IOException {
        App.changeSceneToGame();
    }
}
