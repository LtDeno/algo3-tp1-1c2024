package edu.fiuba;

import edu.configpackage.Configurator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;




//esto no es ni ahi el producto final pero es mas o menos como hariamos para que se renderice el juego en pantalla
public class GameController {

    @FXML
    private GridPane grid;

    private final int cellSize = 16;
    private final Color cellColor = new Color(0, 0, 0, 0.3);
    Configurator config = new Configurator("config.json");
    private final StackPane[][] gridMatrix = new StackPane[config.getnRow()][config.getnCol()];


    public void renderGameElement(GameElement E) {
        var cell = gridMatrix[E.getCoords().getxCoord()][E.getCoords().getyCoord()];
        var label = new Label(E.getName());
        cell.getChildren().add(label);

    }

    public void renderGrid() {
        grid.getChildren().remove(0);
        int columns = config.getnCol();
        int rows = config.getnRow();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                var newStackPane = new StackPane();
                var newRect = new Rectangle(cellSize, cellSize, cellColor);
                newStackPane.getChildren().add(newRect);
                GridPane.setRowIndex(newStackPane, r);
                GridPane.setColumnIndex(newStackPane, c);
                gridMatrix[r][c] = newStackPane;
                grid.getChildren().add(newStackPane);
            }
        }
    }
}
