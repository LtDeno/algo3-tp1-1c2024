package edu.fiuba;

import edu.configpackage.Configurator;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Map;


public class GameController {

    @FXML
    private GridPane grid;

    @FXML
    private Canvas canvas;


    private final int cellSize = 24;
    private final Color cellColor_1 = new Color(0.44, 0.66, 0.88, 1);
    private final Color cellColor_2 = new Color(0.65, 0.82, 1, 1);
    Configurator config = new Configurator("config.json");
    private final String spritesheet = String.valueOf(getClass().getResource("robots.png"));
    private final int spriteSize = 32;


    //creo que seria mejor agregarle un atributo imageX a GameElement para que cada elemento
    //sepa donde esta su sprite en el spritesheet, pero no se si califica como mezclar logica con graficos
    @Deprecated
    private final Map<String, Integer> sprites = Map.of(
            "marito", 0,
            "1x", 5,
            "2x", 9,
            "fueguito", 13
    );

    public void resetCanvas() {
        var gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void renderGameElement(GameElement E) {
        var gc = canvas.getGraphicsContext2D();
        Image sprite = new Image(spritesheet);
        gc.drawImage(sprite, sprites.get(E.getName()) * spriteSize, 0, spriteSize, spriteSize, (E.getCoords().getxCoord() - 1) * cellSize, (E.getCoords().getyCoord() - 1) * cellSize, cellSize, cellSize);

    }

    public void renderGrid() {
        canvas.setHeight(cellSize * config.getnRow());
        canvas.setWidth(cellSize * config.getnCol());
        int columns = config.getnCol();
        int rows = config.getnRow();

        for (int r = 0; r < rows; r++) {
            int correction = r % 2 == 0 ? 0 : 1;
            for (int c = 0; c < columns; c++) {
                var newRect = (c + correction) % 2 == 0 ? new Rectangle(cellSize, cellSize, cellColor_1) : new Rectangle(cellSize, cellSize, cellColor_2);
                GridPane.setRowIndex(newRect, r);
                GridPane.setColumnIndex(newRect, c);
                grid.getChildren().add(newRect);
            }
        }
    }

    public int getCellSize() { return cellSize;}

    public void teleportRandomly() {

    }
    public void teleportSafely() {

    }
    public void waitForRobots() {

    }

}
