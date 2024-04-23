package edu.fiuba;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class GameController {

    @FXML
    private Label levelLabel;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label levelUpLabel;
    @FXML
    public VBox deathVBox;
    @FXML
    private Button randomTeleportButton;
    @FXML
    private Button safeTeleportButton;
    @FXML
    private Button waitForRobotsButton;
    @FXML
    private GridPane grid;
    @FXML
    private Canvas canvas;

    private Game game;
    private Scene scene;

    private final int cellSize = Constants.CELLSIZE;
    private final Color cellColor_1 = Constants.CELLONECOLOR;
    private final Color cellColor_2 = Constants.CELLTWOCOLOR;
    private final String spriteSheet = String.valueOf(getClass().getResource(Constants.ELEMENTSPRITESFILE));
    private final int spriteSize = Constants.SPRITESIZE;
    private double mouseAngle;

    @Deprecated
    private double previousMouseAngle = 0d;

    // fueguito en realidad no tiene animacion, es solo un sprite, asi que puede que cree una clase
    // aparte para los sprites, que tenga como superclase un clase que tambien tenga de subclase Animation
    private final Map<String, Animation> animations = Map.of(
            Constants.CHARACTERNAME, new Animation(0, spriteSize, 150, 3000, Constants.CHARACTERANIMATIONFRAMES),
            Constants.SLOWROBOTNAME, new Animation(5 * spriteSize, spriteSize, 100, 0, Constants.SLOWROBOTANIMATIONFRAMES),
            Constants.FASTROBOTNAME, new Animation(9 * spriteSize, spriteSize, 100, 0, Constants.FASTROBOTANIMATIONFRAMES),
            Constants.FIRENAME, new Animation(13 * spriteSize, spriteSize, 100, 0)
    );
    private final HashMap<KeyCode, Coordinates> keyboardControls = new HashMap<>();
    private final Map<KeyCode, Coordinates> numericControls = Map.of(
            KeyCode.NUMPAD1, Constants.DOWNLEFTCOORDINATES,
            KeyCode.NUMPAD2, Constants.DOWNCOORDINATES,
            KeyCode.NUMPAD3, Constants.DOWNRIGHTCOORDINATES,
            KeyCode.NUMPAD4, Constants.LEFTCOORDINATES,
            KeyCode.NUMPAD5, Constants.MIDDLECOORDINATES,
            KeyCode.NUMPAD6, Constants.RIGHTCOORDINATES,
            KeyCode.NUMPAD7, Constants.UPLEFTCOORDINATES,
            KeyCode.NUMPAD8, Constants.UPCOORDINATES,
            KeyCode.NUMPAD9, Constants.UPRIGHTCOORDINATES
    );
    private final Map<KeyCode, Coordinates> alphaControls = Map.of(
            KeyCode.W, Constants.UPCOORDINATES,
            KeyCode.A, Constants.LEFTCOORDINATES,
            KeyCode.S, Constants.DOWNCOORDINATES,
            KeyCode.D, Constants.RIGHTCOORDINATES,
            KeyCode.Q, Constants.UPLEFTCOORDINATES,
            KeyCode.E, Constants.UPRIGHTCOORDINATES,
            KeyCode.Z, Constants.DOWNLEFTCOORDINATES,
            KeyCode.C, Constants.DOWNRIGHTCOORDINATES,
            KeyCode.X, Constants.MIDDLECOORDINATES
    );
    private final Map<Double, Coordinates> mouseControls = Map.of(
            Math.PI/8, Constants.UPRIGHTCOORDINATES,
            3*Math.PI/8, Constants.UPCOORDINATES,
            5*Math.PI/8, Constants.UPLEFTCOORDINATES,
            7*Math.PI/8, Constants.LEFTCOORDINATES,
            9*Math.PI/8, Constants.DOWNLEFTCOORDINATES,
            11*Math.PI/8, Constants.DOWNCOORDINATES,
            13*Math.PI/8, Constants.DOWNRIGHTCOORDINATES,
            15*Math.PI/8, Constants.RIGHTCOORDINATES,
            -1.0, Constants.MIDDLECOORDINATES
    );
    private final Map<Double, Image> mouseImages = Map.of(
            Math.PI/8, new Image(String.valueOf(getClass().getResource(Constants.UPRIGHTCURSORFILE)), 128d, 128d, true, false),
            3*Math.PI/8, new Image(String.valueOf(getClass().getResource(Constants.UPCURSORFILE)), 128d, 128d, true, false),
            5*Math.PI/8, new Image(String.valueOf(getClass().getResource(Constants.UPLEFTCURSORFILE)), 128d, 128d, true, false),
            7*Math.PI/8, new Image(String.valueOf(getClass().getResource(Constants.LEFTCURSORFILE)), 128d, 128d, true, false),
            9*Math.PI/8, new Image(String.valueOf(getClass().getResource(Constants.DOWNLEFTCURSORFILE)), 128d, 128d, true, false),
            11*Math.PI/8, new Image(String.valueOf(getClass().getResource(Constants.DOWNCURSORFILE)), 128d, 128d, true, false),
            13*Math.PI/8, new Image(String.valueOf(getClass().getResource(Constants.DOWNRIGHTCURSORFILE)), 128d, 128d, true, false),
            15*Math.PI/8, new Image(String.valueOf(getClass().getResource(Constants.RIGHTCURSORFILE)), 128d, 128d, true, false),
            -1.0, new Image(String.valueOf(getClass().getResource(Constants.MIDDLECURSORFILE)), 128d, 128d, true, false)
    );


    void setGame(Game game) {
        this.game = game;
    }

    void setScene(Scene scene) {
        this.scene = scene;
    }

    void load() {

        this.animations.get(Constants.FIRENAME).addFrame(0);
        this.animations.get(Constants.CHARACTERNAME).run();
        this.animations.get(Constants.FASTROBOTNAME).run();
        this.animations.get(Constants.SLOWROBOTNAME).run();
        this.animations.get(Constants.FIRENAME).run();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateGraphics();
            }
        }.start();

        this.renderGrid();
        this.assignEvents();
        this.keyboardControls.putAll(numericControls);
        this.keyboardControls.putAll(alphaControls);
        this.randomTeleportButton.setText("Teleport Randomly\n(Remaining: " + this.game.getCharacter().getRandomTeleportsLeft() + ")");
        this.safeTeleportButton.setText("Teleport Safely\n(Remaining: " + this.game.getCharacter().getSafeTeleportsLeft() + ")");
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
            KeyCode keyPressed = e.getCode();
            Coordinates coordinatesToMove = this.keyboardControls.get(keyPressed);
            if (coordinatesToMove == null) return;

            this.game.characterMove(coordinatesToMove);
            this.update();
        });
        this.canvas.setOnMouseExited(event -> {
            scene.setCursor(Cursor.DEFAULT);
        });

        this.canvas.setOnMouseMoved(e -> {

            double dy = (this.cellSize * (this.game.getCharacter().getCoords().getyCoord() - 0.5)) - (e.getY());
            double dx = e.getX() - (this.cellSize * (this.game.getCharacter().getCoords().getxCoord() - 0.5));
            double clickAngle = Math.abs(dy) - this.cellSize/2.0 < 0 && Math.abs(dx) - this.cellSize/2.0 < 0  ? -1.0 : (dy < 0 ? Math.atan2(-dy, -dx) + Math.PI : Math.atan2(dy, dx));
            Set<Double> keys = this.mouseImages.keySet();
            this.mouseAngle = clickAngle < Math.PI/8 ? (clickAngle < 0 ? -1.0 : 15 * Math.PI/8) : keys.stream().filter(key -> key > clickAngle - Math.PI/4).sorted().findFirst().get();

            // esto va a ser reemplazado por un evento que detecta cuando se cambio el angulo
            if (this.mouseAngle != this.previousMouseAngle) {
                Image mouseImage = this.mouseImages.get(this.mouseAngle);
                ImageCursor imageCursor = new ImageCursor(mouseImage, mouseImage.getWidth() / 2, mouseImage.getHeight() / 2);
                canvas.setCursor(imageCursor);
                this.previousMouseAngle = this.mouseAngle;
            }

        });

        this.scene.setOnMouseClicked(e -> {
            Coordinates coordinatesToMove = this.mouseControls.get(this.mouseAngle);
            if (coordinatesToMove == null) return;

            this.game.characterMove(coordinatesToMove);
            this.update();
        });

        this.randomTeleportButton.setOnAction(event -> {
            this.game.characterTeleport();
            this.update();
        });

        this.safeTeleportButton.setOnAction(event -> {
            this.game.characterTeleportSafely();
            this.update();
        });

        this.waitForRobotsButton.setOnAction(event -> {
            this.game.characterMove(Coordinates.ZERO);
            this.update();
        });
    }
    private void update() {
        this.randomTeleportButton.setText("Teleport Randomly\n(Remaining: " + this.game.getCharacter().getRandomTeleportsLeft() + ")");
        this.safeTeleportButton.setText("Teleport Safely\n(Remaining: " + this.game.getCharacter().getSafeTeleportsLeft() + ")");
        this.levelLabel.setText("Level: " + this.game.getLevel());
        this.scoreLabel.setText("Score: " + this.game.getScore());
        if (this.game.hasGameEnded()) deathVBox.setVisible(true);
        levelUpLabel.setVisible(this.game.isReadyForLevelUp());
    }
    private void updateGraphics() {
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
        gc.drawImage(sprite, this.animations.get(E.getName()).getCurrentX(), 0, this.spriteSize, this.spriteSize, (E.getCoords().getxCoord() - 1) * this.cellSize, (E.getCoords().getyCoord() - 1) * this.cellSize, this.cellSize, this.cellSize);
    }

    public void restartGame() throws IOException {
        App.changeSceneToGame();
    }

    public void endGame() {
        System.exit(0);
    }

}
