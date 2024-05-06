package edu.fiuba;

import edu.fiuba.configpackage.Configurator;
import edu.fiuba.controller.GameController;
import edu.fiuba.controller.StartController;
import edu.fiuba.model.Game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {

    private static final Configurator config = new Configurator(Constants.CONFIGURATIONFILE);
    private static Stage stage;
    private static Scene scene;

    @Override
    public void start(Stage newStage) throws IOException {
        stage = newStage;

        FXMLLoader startLoader = getFXMLLoader(Constants.STARTSCENEFXML);
        scene = new Scene(startLoader.load());
        StartController startController = startLoader.getController();
        startController.load(config);

        stage.setTitle(Constants.GAMENAME);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.exit(0);
    }

    public static void changeSceneToGame() throws IOException {
        FXMLLoader gameLoader = getFXMLLoader(Constants.GAMESCENEFXML);
        scene = new Scene(gameLoader.load());
        GameController gameController = gameLoader.getController();

        gameController.setGame(new Game(config));
        gameController.setScene(scene);
        gameController.load();

        stage.setScene(scene);
        stage.sizeToScene();
        stage.centerOnScreen();
        stage.show();
    }

    private static FXMLLoader getFXMLLoader(String fxml){
        return new FXMLLoader(App.class.getResource(fxml + Constants.FXMLFILEEXTENSION));
    }

    public static void main(String[] args) {
        launch();

    }
}