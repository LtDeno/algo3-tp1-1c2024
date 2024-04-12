package edu.fiuba;

import edu.configpackage.Configurator;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


//esto no es ni ahi el producto final pero es mas o menos como hariamos para que se imprima el grid en pantalla
public class GameController {

    @FXML
    private GridPane grid;

    private final int squareSize = 16;
    private final Color squareColor = new Color(0, 0, 0, 1);
    Configurator config = new Configurator("config.json");

    public void renderGrid() {
        grid.getChildren().remove(0);
        int columns = config.getnCol();
        int rows = config.getnRow();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                var newRect = new Rectangle(squareSize, squareSize, squareColor);
                GridPane.setRowIndex(newRect, r);
                GridPane.setColumnIndex(newRect, c);
                grid.getChildren().add(newRect);
            }
        }
    }
}
