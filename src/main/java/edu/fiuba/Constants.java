package edu.fiuba;

import edu.fiuba.model.Coordinates;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public static final Map<KeyCode, Coordinates> NUMERICCONTROLS = Map.of(
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
    public static final Map<KeyCode, Coordinates> ALPHACONTROLS = Map.of(
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
    public static final Map<Double, Coordinates> MOUSECONTROLS = Map.of(
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
    public static final ArrayList<Integer> CHARACTERANIMATIONFRAMES = new ArrayList<>(List.of(0, 1, 2, 3, 2, 1, 0, 1, 2, 3, 2, 1, 0));
    public static final ArrayList<Integer> SLOWROBOTANIMATIONFRAMES = new ArrayList<>(List.of(0, 1, 2, 3));
    public static final ArrayList<Integer> FASTROBOTANIMATIONFRAMES = new ArrayList<>(List.of(0, 1, 2, 3));
    public static final ArrayList<Integer> FIREANIMATIONFRAMES = new ArrayList<>(List.of(0));
    public static String UPRIGHTCURSORFILE = "uprightcursor.png";
    public static String UPCURSORFILE = "upcursor.png";
    public static String UPLEFTCURSORFILE = "upleftcursor.png";
    public static String LEFTCURSORFILE = "leftcursor.png";
    public static String DOWNLEFTCURSORFILE = "downleftcursor.png";
    public static String DOWNCURSORFILE = "downcursor.png";
    public static String DOWNRIGHTCURSORFILE = "downrightcursor.png";
    public static String RIGHTCURSORFILE = "rightcursor.png";
    public static String MIDDLECURSORFILE = "middlecursor.png";
}
