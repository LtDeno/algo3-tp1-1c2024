package edu.fiuba;

import edu.configpackage.Configurator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;
import java.util.Set;


/**
 * JavaFX App
 */
public class App extends Application {

    Configurator config = new Configurator("config.json");
    Game game = new Game(config, this.getCharacterName());
    GameController gameController;

    private final Map<KeyCode, Action> keyboardControls = Map.of(
            KeyCode.NUMPAD7, new ActionMove(game.getCharacter(), new Coordinates(-1, -1), game.getGrid()),
            KeyCode.NUMPAD8, new ActionMove(game.getCharacter(), new Coordinates(0, -1), game.getGrid()),
            KeyCode.NUMPAD9, new ActionMove(game.getCharacter(), new Coordinates(1, -1), game.getGrid()),
            KeyCode.NUMPAD4, new ActionMove(game.getCharacter(), new Coordinates(-1, 0), game.getGrid()),
            KeyCode.NUMPAD5, new ActionMove(game.getCharacter(), new Coordinates(0, 0), game.getGrid()),
            KeyCode.NUMPAD6, new ActionMove(game.getCharacter(), new Coordinates(1, 0), game.getGrid()),
            KeyCode.NUMPAD1, new ActionMove(game.getCharacter(), new Coordinates(-1, 1), game.getGrid()),
            KeyCode.NUMPAD2, new ActionMove(game.getCharacter(), new Coordinates(0, 1), game.getGrid()),
            KeyCode.NUMPAD3, new ActionMove(game.getCharacter(), new Coordinates(1, 1), game.getGrid())
    );

    private final Map<Double, Action> mouseControls = Map.of(
            Math.PI/8, new ActionMove(game.getCharacter(), new Coordinates(1, -1), game.getGrid()),
            3*Math.PI/8, new ActionMove(game.getCharacter(), new Coordinates(0, -1), game.getGrid()),
            5*Math.PI/8, new ActionMove(game.getCharacter(), new Coordinates(-1, -1), game.getGrid()),
            7*Math.PI/8, new ActionMove(game.getCharacter(), new Coordinates(-1, 0), game.getGrid()),
            9*Math.PI/8, new ActionMove(game.getCharacter(), new Coordinates(-1, 1), game.getGrid()),
            11*Math.PI/8, new ActionMove(game.getCharacter(), new Coordinates(0, 1), game.getGrid()),
            13*Math.PI/8, new ActionMove(game.getCharacter(), new Coordinates(1, 1), game.getGrid()),
            15*Math.PI/8, new ActionMove(game.getCharacter(), new Coordinates(1, 0), game.getGrid()),
            -1.0, new ActionMove(game.getCharacter(), new Coordinates(0, 0), game.getGrid())
    );


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* Cosas que vienen por defecto*/
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        //scene = new Scene(loadFXML("primary"), 640, 480);

        var fxmlLoader = getFXMLLoader("grid");
        scene = new Scene(fxmlLoader.load());
        this.gameController = fxmlLoader.getController();
        gameController.renderGrid();
        game.getGrid().getGameElements().forEach(e -> {
            gameController.renderGameElement(e);
        });
        scene.setOnKeyPressed(e -> {
            KeyCode keyPressed = e.getCode();
            Action action = keyboardControls.get(keyPressed);
            if (action == null) {
                return;
            }
            action.actuate();
            this.update();
        });
        scene.setOnMouseClicked(e -> {
            Double dy = (gameController.getCellSize() * (game.getCharacter().getCoords().getyCoord() - 0.5)) - e.getSceneY();
            Double dx = e.getSceneX() - (gameController.getCellSize() * (game.getCharacter().getCoords().getxCoord() - 0.5));
            Double clickAngle = Math.abs(dy) - gameController.getCellSize()/2.0 < 0 && Math.abs(dx) - gameController.getCellSize()/2.0 < 0  ? -1.0 : (dy < 0 ? Math.atan2(-dy, -dx) + Math.PI : Math.atan2(dy, dx));
            Set<Double> keys = mouseControls.keySet();
            Double result = clickAngle < Math.PI/8 ? (clickAngle < 0 ? -1.0 : 15 * Math.PI/8) : keys.stream().filter(key -> key > clickAngle - Math.PI/4).sorted().findFirst().get();
            Action action = mouseControls.get(result);
            if (action == null) {
                return;
            }
            action.actuate();
            this.update();
        });

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


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private static FXMLLoader getFXMLLoader(String fxml){
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader;
    }

    public static void main(String[] args) {
        launch();
    }

    String getCharacterName() {
        String name = "marito";
        return name;
    }

    void update() {

        gameController.resetCanvas();
        game.getGrid().getGameElements().forEach(e -> {
            if (e.getClass() != game.getCharacter().getClass()) {
                e.moveInDirection(
                        new Coordinates(game.getCharacter().getCoords().getxCoord() - e.getCoords().getxCoord(), game.getCharacter().getCoords().getyCoord() - e.getCoords().getyCoord()),
                        game.getGrid()
                );
            }
            gameController.renderGameElement(e);
        });
    }
}