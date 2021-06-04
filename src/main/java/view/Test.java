package view;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Test extends Application {
    public void start(Stage theStage) {
        theStage.setTitle("Keyboard Example");

        Group root = new Group();
        Scene theScene = new Scene(root);
        theStage.setScene(theScene);

        Canvas canvas = new Canvas(512 - 64, 256);
        root.getChildren().add(canvas);

        ArrayList<String> input = new ArrayList<String>();

        theScene.setOnKeyPressed(
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent e) {
                        String code = e.getCode().toString();

                        // only add once... prevent duplicates
                        if (!input.contains(code))
                            input.add(code);
                    }
                });

        theScene.setOnKeyReleased(
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent e) {
                        String code = e.getCode().toString();
                        input.remove(code);
                    }
                });

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image left = new Image(String.valueOf(getClass().getResource("img/Ghost1.png")));
        Image leftG = new Image(String.valueOf(getClass().getResource("img/Ghost2.png")));

        Image right = new Image(String.valueOf(getClass().getResource("img/Ghost3.png")));
        Image rightG = new Image(String.valueOf(getClass().getResource("img/Ghost4.png")));

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                // Clear the canvas
                gc.clearRect(0, 0, 512, 512);

                if (input.contains("LEFT"))
                    gc.drawImage(leftG, 64, 64);
                else
                    gc.drawImage(left, 64, 64);

                if (input.contains("RIGHT"))
                    gc.drawImage(rightG, 256, 64);
                else
                    gc.drawImage(right, 256, 64);
            }
        }.start();

        theStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
