package view;

import controller.GameBoardObject;
import controller.MapController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MapMenu extends Menu {
    private static String username;
    private static Stage stage;
    private static int life = 3;
    private static MapController mapController;
    private static GameBoardObject[][] gameBoard;
    private static Menu parentMenu;

    public GridPane lifeBoard;
    public GridPane mapBoard = new GridPane();
    public Button nextMapButton;
    public Button previousMapButton;
    public Button generateMapButton;
    public Button saveMapButton;
    public Button decreaseLifeButton;
    public Button increaseLifeButton;
    public Button startGameButton;
    public Button backButton;

    @Override
    public void start(Stage stage) throws IOException {
        MapMenu.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("MapMenu.fxml"));
        Scene scene = new Scene(root, 1080, 720);
        scene.getStylesheets().add(String.valueOf(getClass().getResource("style/style.css")));
        stage.setScene(scene);
        stage.setTitle("Pacman");
        stage.show();
    }

    @FXML
    public void initialize() {
        updateHeart();
        mapController.nextMap();
        updateBoard();
    }

    public static void setGameBoard(GameBoardObject[][] gameBoard) {
        MapMenu.gameBoard = gameBoard;
    }

    public static void setParentMenu(Menu parentMenu) {
        MapMenu.parentMenu = parentMenu;
    }

    private void updateHeart() {
        lifeBoard.getChildren().clear();
        for (int i = 0; i < life; i++) lifeBoard.add(Map.getHeart(), i, 0);
    }

    public void increaseLife(MouseEvent mouseEvent) {
        if (life < 5) {
            life++;
            updateHeart();
        }
    }

    public void decreaseLife(MouseEvent mouseEvent) {
        if (life > 2) {
            life--;
            updateHeart();
        }
    }

    public void startGame(MouseEvent mouseEvent) throws IOException {
        GameMenu gameMenu = mapController.startGame(life);
        GameMenu.setExitMenu(parentMenu);
        gameMenu.start(stage);
    }

    public void nextMap(MouseEvent mouseEvent) {
        mapController.nextMap();
        updateBoard();
    }

    public void previousMap(MouseEvent mouseEvent) {
        mapController.previousMap();
        updateBoard();
    }

    public void generateMap(MouseEvent mouseEvent) {
        mapController.generateMap();
    }

    public void saveMap(MouseEvent mouseEvent) {
        mapController.saveMap();
    }

    public void setUsername(String username) {
        MapMenu.username = username;
        MapMenu.mapController = new MapController(username);
    }

    public void updateBoard() {
        mapBoard.getChildren().clear();
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                mapBoard.add(Map.getMapShape(gameBoard[i][j]), j, i);
            }
        }
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        parentMenu.start(stage);
    }
}
