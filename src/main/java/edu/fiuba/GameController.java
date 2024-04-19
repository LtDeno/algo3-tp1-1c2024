package edu.fiuba;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Map;
import java.util.Set;


public class GameController {

    @FXML
    private Button safeTeleportButton;
    @FXML
    private Button randomTeleportButton;
    @FXML
    private Button waitForRobotsButton;
    @FXML
    private GridPane grid;
    @FXML
    private Canvas canvas;

    private Game game;
    private Scene scene;

    private final int cellSize = 24;
    private final Color cellColor_1 = new Color(0.44, 0.66, 0.88, 1);
    private final Color cellColor_2 = new Color(0.65, 0.82, 1, 1);
    private final String spriteSheet = String.valueOf(getClass().getResource("robots.png"));
    private final int spriteSize = 32;
    //creo que seria mejor agregarle un atributo imageX a GameElement para que cada elemento
    //sepa donde esta su sprite en el spritesheet, pero no se si califica como mezclar logica con graficos
    private final Map<String, Integer> sprites = Map.of(
            "marito", 0,
            "1x", 5,
            "2x", 9,
            "fueguito", 13
    );
    private final Map<KeyCode, Coordinates> keyboardControls = Map.of(
            KeyCode.NUMPAD7, new Coordinates(-1, -1),
            KeyCode.NUMPAD8, new Coordinates(0, -1),
            KeyCode.NUMPAD9, new Coordinates(1, -1),
            KeyCode.NUMPAD4, new Coordinates(-1, 0),
            KeyCode.NUMPAD5, Coordinates.ZERO,
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
            -1.0, Coordinates.ZERO
    );

    void setGame(Game game) {
        this.game = game;
    }

    void setScene(Scene scene) {
        this.scene = scene;
    }

    public void load() {
        this.renderGrid();
        this.assignEvents();
    }

    private void renderGrid() {
        int columns = this.game.getGrid().getnColumns();
        int rows = this.game.getGrid().getnRows();
        this.canvas.setWidth(this.cellSize * columns);
        this.canvas.setHeight(this.cellSize * rows);

        for (int r = 0; r < rows; r++) {
            int correction = r % 2 == 0 ? 0 : 1;
            for (int c = 0; c < columns; c++) {
                var newRect = (c + correction) % 2 == 0 ? new Rectangle(this.cellSize, this.cellSize, cellColor_1) : new Rectangle(this.cellSize, this.cellSize, cellColor_2);
                GridPane.setRowIndex(newRect, r);
                GridPane.setColumnIndex(newRect, c);
                this.grid.getChildren().add(newRect);
            }
        }

        this.game.getGrid().getGameElements().forEach(this::renderGameElement);
    }

    private void assignEvents() {
        scene.setOnKeyPressed(e -> {
            if (this.game.levelUp()) return;

            KeyCode keyPressed = e.getCode();
            Coordinates coordinatesToMove = this.keyboardControls.get(keyPressed);
            if (coordinatesToMove == null) return;

            this.game.characterMove(coordinatesToMove);
            this.update();
        });

        this.scene.setOnMouseClicked(e -> {
            double dy = (this.cellSize * (this.game.getCharacter().getCoords().getyCoord() - 0.5)) - e.getSceneY();
            double dx = e.getSceneX() - (this.cellSize * (this.game.getCharacter().getCoords().getxCoord() - 0.5));
            double clickAngle = Math.abs(dy) - this.cellSize/2.0 < 0 && Math.abs(dx) - this.cellSize/2.0 < 0  ? -1.0 : (dy < 0 ? Math.atan2(-dy, -dx) + Math.PI : Math.atan2(dy, dx));
            Set<Double> keys = this.mouseControls.keySet();
            Double result = clickAngle < Math.PI/8 ? (clickAngle < 0 ? -1.0 : 15 * Math.PI/8) : keys.stream().filter(key -> key > clickAngle - Math.PI/4).sorted().findFirst().get();
            Coordinates coordinatesToMove = this.mouseControls.get(result);
            if (coordinatesToMove == null) return;

            this.game.characterMove(coordinatesToMove);
            this.update();
        });
    }

    private void update() {
        this.resetCanvas();
        this.game.getGrid().getGameElements().forEach(this::renderGameElement);
    }

    private void resetCanvas() {
        var gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void renderGameElement(GameElement E) {
        var gc = this.canvas.getGraphicsContext2D();
        Image sprite = new Image(this.spriteSheet);
        gc.drawImage(sprite, this.sprites.get(E.getName()) * this.spriteSize, 0, this.spriteSize, this.spriteSize, (E.getCoords().getxCoord() - 1) * this.cellSize, (E.getCoords().getyCoord() - 1) * this.cellSize, this.cellSize, this.cellSize);

    }

    public void buttonPressedAction(ActionEvent actionEvent) {

    }
}
