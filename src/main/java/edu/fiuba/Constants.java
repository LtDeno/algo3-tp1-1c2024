package edu.fiuba;

import javafx.scene.paint.Color;

public class Constants {
    public static String CONFIGURATIONFILE = "config.json";
    public static String ELEMENTSPRITESFILE = "robots.png";
    public static String PLAYERNAME = "marito";
    public static String SLOWROBOTNAME = "1x";
    public static String FASTROBOTNAME = "2x";
    public static String FIRENAME = "fueguito";
    public static Color CELLONECOLOR = new Color(0.44, 0.66, 0.88, 1);
    public static Color CELLTWOCOLOR = new Color(0.65, 0.82, 1, 1);
    public static int CELLSIZE = 24;
    public static int SPRITESIZE = 32;
    public static Coordinates UPRIGHTCOORDINATES = new Coordinates(1, 1);
    public static Coordinates UPCOORDINATES = new Coordinates(0, 1);
    public static Coordinates UPLEFTCOORDINATES = new Coordinates(-1, 1);
    public static Coordinates RIGHTCOORDINATES = new Coordinates(1, 0);
    public static Coordinates LEFTCOORDINATES = new Coordinates(-1, 0);
    public static Coordinates DOWNRIGHTCOORDINATES = new Coordinates(1, -1);
    public static Coordinates DOWNCOORDINATES = new Coordinates(0, -1);
    public static Coordinates DOWNLEFTCOORDINATES = new Coordinates(-1, -1);
}
