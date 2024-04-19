package edu.fiuba;

import edu.configpackage.Configurator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


/**
 * JavaFX App
 */
public class App extends Application {

    private final Configurator config = new Configurator("config.json");
    private GameController gameController;
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = getFXMLLoader("grid");
        scene = new Scene(fxmlLoader.load());

        this.gameController = fxmlLoader.getController();
        this.gameController.setGame(new Game(config));
        this.gameController.setScene(scene);
        this.gameController.load();

        stage.setResizable(false);
        stage.sizeToScene();
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    private static FXMLLoader getFXMLLoader(String fxml){
        return new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    }

    public static void main(String[] args) {
        launch();
    }
}