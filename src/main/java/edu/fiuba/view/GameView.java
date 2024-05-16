package edu.fiuba.view;

import edu.fiuba.Constants;
import edu.fiuba.model.GameElement;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Map;

public class GameView {

    private final GridPane gridPane;
    private final Canvas canvas;
    private final String spriteSheet = String.valueOf(getClass().getResource(Constants.ELEMENTSPRITESFILE));
    private final int spriteSize = Constants.SPRITESIZE;
    private final int cellSize = Constants.CELLSIZE;
    private final Color cellColor_1 = Constants.CELLONECOLOR;
    private final Color cellColor_2 = Constants.CELLTWOCOLOR;

    private final Map<String, Animation> animations = Map.of(
            Constants.CHARACTERNAME, new Animation(0, spriteSize, 150, 3000, Constants.CHARACTERANIMATIONFRAMES),
            Constants.SLOWROBOTNAME, new Animation(5 * spriteSize, spriteSize, 100, 0, Constants.SLOWROBOTANIMATIONFRAMES),
            Constants.FASTROBOTNAME, new Animation(9 * spriteSize, spriteSize, 100, 0, Constants.FASTROBOTANIMATIONFRAMES),
            Constants.FIRENAME, new Animation(13 * spriteSize, spriteSize, 100, 0, Constants.FIREANIMATIONFRAMES)
    );

    public GameView(Canvas canvas, GridPane gridPane) {
        this.canvas = canvas;
        this.gridPane = gridPane;
    }

    public void renderGrid(int columns, int rows) {
        this.canvas.setWidth(this.cellSize * columns);
        this.canvas.setHeight(this.cellSize * rows);

        for (int r = 0; r < rows; r++) {
            int correction = r % 2 == 0 ? 0 : 1;
            for (int c = 0; c < columns; c++) {
                var newRect = (c + correction) % 2 == 0 ? new Rectangle(this.cellSize, this.cellSize, cellColor_1) : new Rectangle(this.cellSize, this.cellSize, cellColor_2);
                GridPane.setRowIndex(newRect, r);
                GridPane.setColumnIndex(newRect, c);
                this.gridPane.getChildren().add(newRect);
            }
        }
    }

    public void renderGameElement(GameElement E) {
        var gc = this.canvas.getGraphicsContext2D();
        Image sprite = new Image(this.spriteSheet);
        gc.drawImage(sprite, this.animations.get(E.getName()).getCurrentX(), 0, this.spriteSize, this.spriteSize, (E.getCoords().getxCoord() - 1) * this.cellSize, (E.getCoords().getyCoord() - 1) * this.cellSize, this.cellSize, this.cellSize);
    }

    public void setAnimations() {
        animations.values().forEach(Animation::run);
    }

    public void resetCanvas() {
        var gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}
