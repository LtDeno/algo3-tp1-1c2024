package edu.fiuba.controller;

import edu.fiuba.App;
import edu.fiuba.Constants;
import edu.fiuba.model.Coordinates;
import edu.fiuba.model.Game;
import edu.fiuba.view.GameView;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

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
    private VBox deathVBox;
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
    @FXML
    private StackPane gridContainer;

    private Game game;
    private GameView gameView;
    private Scene scene;
    private final HashMap<KeyCode, Coordinates> keyboardControls = new HashMap<>();

    private final int cellSize = Constants.CELLSIZE;

    private double cursorDirectionAngle;
    private double previousCursorDirectionAngle= 0d;

    private boolean PlayerChoosingCellToTeleport = false;

    private final Map<Double, Image> cursorImages = Map.of(
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

    public void setGame(Game game) {
        this.game = game;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void load() {

        this.gameView = new GameView(canvas, grid);
        gameView.setAnimations();
        gameView.renderGrid(this.game.getGrid().getnColumns(), this.game.getGrid().getnRows());

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateGraphics();
            }
        }.start();

        this.assignEvents();
        this.keyboardControls.putAll(Constants.NUMERICCONTROLS);
        this.keyboardControls.putAll(Constants.ALPHACONTROLS);
        this.update();
    }

    private void assignEvents() {

        this.canvas.setOnMouseExited(event -> scene.setCursor(Cursor.DEFAULT));

        this.canvas.setOnMouseMoved(e -> {
            if (PlayerChoosingCellToTeleport) { return; }
            double dy = (this.cellSize * (this.game.getCharacter().getCoords().getyCoord() - 0.5)) - (e.getY());
            double dx = e.getX() - (this.cellSize * (this.game.getCharacter().getCoords().getxCoord() - 0.5));
            double cursorAngleToCharacter = Math.abs(dy) - this.cellSize/2.0 < 0 && Math.abs(dx) - this.cellSize/2.0 < 0  ? -1.0 : (dy < 0 ? Math.atan2(-dy, -dx) + Math.PI : Math.atan2(dy, dx));
            Set<Double> keys = this.cursorImages.keySet();
            this.cursorDirectionAngle = cursorAngleToCharacter < Math.PI/8 ? (cursorAngleToCharacter < 0 ? -1.0 : 15 * Math.PI/8) : keys.stream().filter(key -> key > cursorAngleToCharacter - Math.PI/4).sorted().findFirst().get();

            if (this.cursorDirectionAngle != this.previousCursorDirectionAngle) {
                Image mouseImage = this.cursorImages.get(this.cursorDirectionAngle);
                ImageCursor imageCursor = new ImageCursor(mouseImage, mouseImage.getWidth() / 2, mouseImage.getHeight() / 2);
                canvas.setCursor(imageCursor);
                this.previousCursorDirectionAngle = this.cursorDirectionAngle;
            }
        });

        this.canvas.setOnMouseClicked(e -> {
            if (PlayerChoosingCellToTeleport) {
                PlayerChoosingCellToTeleport = false;
                Coordinates selectedCell = new Coordinates((int)(e.getX()  / cellSize) + 1, (int)(e.getY() / cellSize) + 1);
                this.game.characterTeleportSafely(selectedCell);
                Image mouseImage = this.cursorImages.get(this.cursorDirectionAngle);
                ImageCursor imageCursor = new ImageCursor(mouseImage, mouseImage.getWidth() / 2, mouseImage.getHeight() / 2);
                canvas.setCursor(imageCursor);
                this.update();
                return;
            }
            Coordinates coordinatesToMove = Constants.MOUSECONTROLS.get(this.cursorDirectionAngle);
            if (coordinatesToMove == null) return;
            this.game.characterMove(coordinatesToMove);
            this.update();
        });

        this.scene.setOnKeyPressed(e -> {
            KeyCode keyPressed = e.getCode();
            Coordinates coordinatesToMove = this.keyboardControls.get(keyPressed);
            if (coordinatesToMove == null) return;

            this.game.characterMove(coordinatesToMove);
            this.update();
        });

        this.randomTeleportButton.setOnAction(event -> {
            this.game.characterTeleport();
            this.update();
        });

        this.safeTeleportButton.setOnAction(event -> {
            int tp;
            try {
                tp = Integer.parseInt(this.game.getCharacter().getSafeTeleportsLeft());
            } catch (Exception e) {
                tp = -1;
            }
            if (tp == 0) return;
            this.PlayerChoosingCellToTeleport = true;
            canvas.setCursor(Cursor.HAND);
        });

        this.waitForRobotsButton.setOnAction(event -> {
            this.game.characterMove(Constants.MIDDLECOORDINATES);
            this.update();
        });
    }

    private void update() {
        this.randomTeleportButton.setText("Teleport Randomly\n(Remaining: " + this.game.getCharacter().getRandomTeleportsLeft() + ")");
        this.safeTeleportButton.setText("Teleport Safely\n(Remaining: " + this.game.getCharacter().getSafeTeleportsLeft() + ")");
        this.levelLabel.setText("Level: " + this.game.getLevel());
        this.scoreLabel.setText("Score: " + this.game.getScore());
    }

    private void updateGraphics() {
        this.deathVBox.setVisible(this.game.hasGameEnded());
        this.levelUpLabel.setVisible(this.game.isReadyForLevelUp());
        gameView.resetCanvas();
        this.game.getGrid().getGameElements().forEach(E -> gameView.renderGameElement(E));
    }

    public void restartGame() throws IOException {
        App.changeSceneToGame();
    }

    public void endGame() {
        System.exit(0);
    }

}
