package view;

import controller.GameBoardObject;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.HashMap;

public class Map {

    private static final HashMap<String, Image> imagesHashMap;

    static {
        imagesHashMap = new HashMap<>();
        String[] gameObjectNames = {"PacmanUp", "PacmanDown", "PacmanRight", "PacmanLeft", "PacmanConstant",
                "Ghost1", "Ghost2", "Ghost3", "Ghost4", "GhostScared"};

        for (String objectName : gameObjectNames) {
            Image image = new Image(String.valueOf(Map.class.getResource("img/" + objectName + ".gif")));
            imagesHashMap.put(objectName, image);
        }

        Image heartImage = new Image(String.valueOf(Map.class.getResource("img/Heart.png")));
        imagesHashMap.put("Heart", heartImage);

        Image pointImage = new Image(String.valueOf(Map.class.getResource("img/Point.png")));
        imagesHashMap.put("Point", pointImage);

        Image energyImage = new Image(String.valueOf(Map.class.getResource("img/Energy.png")));
        imagesHashMap.put("Energy", energyImage);
    }

    public static Shape getMapShape(GameBoardObject gameObject) {
        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(20);
        rectangle.setWidth(20);
        if (gameObject == GameBoardObject.WALL) {
            rectangle.setFill(Paint.valueOf("#4203c9"));

        } else if (gameObject == GameBoardObject.PATH) {
            rectangle.setFill(Paint.valueOf("#141414"));

        } else {
            Image image = imagesHashMap.get(gameObject.toString());
            rectangle.setFill(new ImagePattern(image));
        }
        return rectangle;
    }

    public static Rectangle getHeart() {
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(36);
        rectangle.setHeight(36);
        Image image = imagesHashMap.get("Heart");
        rectangle.setFill(new ImagePattern(image));
        return rectangle;
    }
}
