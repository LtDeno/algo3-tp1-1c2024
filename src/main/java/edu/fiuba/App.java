package edu.fiuba;

import edu.configpackage.Configurator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {

    private static final Configurator config = new Configurator(Constants.CONFIGURATIONFILE);
    private static GameController gameController;
    private static Stage stage;
    private static Scene scene;

    @Override
    public void start(Stage newStage) throws IOException {
        stage = newStage;

        FXMLLoader startLoader = getFXMLLoader(Constants.STARTSCENEFXML);
        scene = new Scene(startLoader.load());

        stage.setTitle(Constants.GAMENAME);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public static void changeSceneToGame() throws IOException {
        FXMLLoader gridLoader = getFXMLLoader(Constants.GAMESCENEFXML);
        scene = new Scene(gridLoader.load());
        gameController = gridLoader.getController();

        gameController.setGame(new Game(config));
        gameController.setScene(scene);
        gameController.load();

        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    private static FXMLLoader getFXMLLoader(String fxml){
        return new FXMLLoader(App.class.getResource(fxml + Constants.FXMLFILEEXTENSION));
    }

    public static void main(String[] args) {
        launch();
    }
}