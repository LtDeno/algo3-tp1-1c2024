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
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* Cosas que vienen por defecto*/
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        //scene = new Scene(loadFXML("primary"), 640, 480);
        scene = new Scene(loadFXML("grid"), 640, 480);
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
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Configurator config = new Configurator("config.json");
    Game game = new Game(config, this.getCharacterName());

    public static void main(String[] args) {
        launch();
    }

    String getCharacterName() {
        String name = "";
        return name;
    }

}