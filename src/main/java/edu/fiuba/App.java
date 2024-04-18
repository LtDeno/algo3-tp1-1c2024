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

    private final Configurator config = new Configurator("config.json");
    private final Game game = new Game(config, this.getCharacterName());
    private GameController gameController;
    private static Scene scene;
    private final Map<KeyCode, Coordinates> keyboardControls = Map.of(
            KeyCode.NUMPAD7, new Coordinates(-1, -1),
            KeyCode.NUMPAD8, new Coordinates(0, -1),
            KeyCode.NUMPAD9, new Coordinates(1, -1),
            KeyCode.NUMPAD4, new Coordinates(-1, 0),
            KeyCode.NUMPAD5, new Coordinates(0, 0),
            KeyCode.NUMPAD6, new Coordinates(1, 0),
            KeyCode.NUMPAD1, new Coordinates(-1, 1),
            KeyCode.NUMPAD2, new Coordinates(0, 1),
            KeyCode.NUMPAD3, new Coordinates(1, 1)
    );
    private final Map<Double, Coordinates> mouseControls = Map.of(
            Math.PI/8, new Coordinates(1, -1),
            3*Math.PI/8, new Coordinates(0, -1),
            5*Math.PI/8, new Coordinates(-1, -1),
            7*Math.PI/8, new Coordinates(-1, 0),
            9*Math.PI/8, new Coordinates(-1, 1),
            11*Math.PI/8, new Coordinates(0, 1),
            13*Math.PI/8, new Coordinates(1, 1),
            15*Math.PI/8, new Coordinates(1, 0),
            -1.0, new Coordinates(0, 0)
    );

    @Override
    public void start(Stage stage) throws IOException {
        var fxmlLoader = getFXMLLoader("grid");
        scene = new Scene(fxmlLoader.load());

        this.gameController = fxmlLoader.getController();
        this.gameController.setConfig(this.config);
        this.gameController.renderGrid();

        this.game.getGrid().getGameElements().forEach(e -> this.gameController.renderGameElement(e));

        scene.setOnKeyPressed(e -> {
            if (this.game.levelUp()) return;

            KeyCode keyPressed = e.getCode();
            Coordinates coordinatesToMove = this.keyboardControls.get(keyPressed);
            if (coordinatesToMove == null) return;

            this.game.characterMove(coordinatesToMove);
            this.update();
        });

        scene.setOnMouseClicked(e -> {
            if (this.game.levelUp()) return;

            double dy = (this.gameController.getCellSize() * (this.game.getCharacter().getCoords().getyCoord() - 0.5)) - e.getSceneY();
            double dx = e.getSceneX() - (this.gameController.getCellSize() * (this.game.getCharacter().getCoords().getxCoord() - 0.5));
            double clickAngle = Math.abs(dy) - this.gameController.getCellSize()/2.0 < 0 && Math.abs(dx) - this.gameController.getCellSize()/2.0 < 0  ? -1.0 : (dy < 0 ? Math.atan2(-dy, -dx) + Math.PI : Math.atan2(dy, dx));
            Set<Double> keys = this.mouseControls.keySet();
            Double result = clickAngle < Math.PI/8 ? (clickAngle < 0 ? -1.0 : 15 * Math.PI/8) : keys.stream().filter(key -> key > clickAngle - Math.PI/4).sorted().findFirst().get();
            Coordinates coordinatesToMove = this.mouseControls.get(result);
            if (coordinatesToMove == null) return;

            this.game.characterMove(coordinatesToMove);
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

    private static FXMLLoader getFXMLLoader(String fxml){
        return new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    }

    public static void main(String[] args) {
        launch();
    }

    String getCharacterName() {
        return "marito";
    }

    void update() {
        gameController.resetCanvas();
        game.getGrid().getGameElements().forEach(e -> gameController.renderGameElement(e));
    }
}