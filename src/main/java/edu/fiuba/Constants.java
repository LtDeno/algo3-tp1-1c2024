package edu.fiuba;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Constants {
    public static final String GAMENAME = "Robots Chasing You Until You Pass Out";
    public static final String STARTSCENEFXML = "start";
    public static final String GAMESCENEFXML = "grid";
    public static final String FXMLFILEEXTENSION = ".fxml";
    public static final String CONFIGURATIONFILE = "config.json";
    public static final String ELEMENTSPRITESFILE = "robots.png";
    public static final String CHARACTERNAME = "marito";
    public static final String SLOWROBOTNAME = "1x";
    public static final String FASTROBOTNAME = "2x";
    public static final String FIRENAME = "fueguito";
    public static final Color CELLONECOLOR = new Color(0.44, 0.66, 0.88, 1);
    public static final Color CELLTWOCOLOR = new Color(0.65, 0.82, 1, 1);
    public static final int CELLSIZE = 24;
    public static final int SPRITESIZE = 32;
    public static final Coordinates UPRIGHTCOORDINATES = new Coordinates(1, -1);
    public static final Coordinates UPCOORDINATES = new Coordinates(0, -1);
    public static final Coordinates UPLEFTCOORDINATES = new Coordinates(-1, -1);
    public static final Coordinates RIGHTCOORDINATES = new Coordinates(1, 0);
    public static final Coordinates MIDDLECOORDINATES = new Coordinates(0, 0);
    public static final Coordinates LEFTCOORDINATES = new Coordinates(-1, 0);
    public static final Coordinates DOWNRIGHTCOORDINATES = new Coordinates(1, 1);
    public static final Coordinates DOWNCOORDINATES = new Coordinates(0, 1);
    public static final Coordinates DOWNLEFTCOORDINATES = new Coordinates(-1, 1);
    public static final ArrayList<Integer> CHARACTERANIMATIONFRAMES = new ArrayList<>(List.of(0, 1, 2, 3, 2, 1, 0, 1, 2, 3, 2, 1, 0));
    public static final ArrayList<Integer> SLOWROBOTANIMATIONFRAMES = new ArrayList<>(List.of(0, 1, 2, 3));
    public static final ArrayList<Integer> FASTROBOTANIMATIONFRAMES = new ArrayList<>(List.of(0, 1, 2, 3));

}
