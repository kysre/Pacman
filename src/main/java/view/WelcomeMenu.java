package view;

import controller.DatabaseController;
import controller.GameController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.game.GameBoard;

import java.io.IOException;

public class WelcomeMenu extends Menu {

    private static Stage stage;
    @FXML
    public Button instantGameButton;
    public Button loginSignUpButton;
    public BorderPane pageBorderPane;


    @Override
    public void start(Stage stage) throws IOException {
        Sound.playSound("menu");
        WelcomeMenu.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("WelcomeMenu.fxml"));
        Scene scene = new Scene(root, 1080, 720);
        scene.getStylesheets().add(String.valueOf(getClass().getResource("style/style.css")));
        stage.setScene(scene);
        stage.setTitle("Pacman");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void loginSignUp(MouseEvent mouseEvent) throws IOException {
        LoginSignUpMenu loginMenu = new LoginSignUpMenu();
        loginMenu.setParentMenu(this);
        loginMenu.start(stage);
    }

    public void startGame(MouseEvent mouseEvent) throws IOException {


        MapMenu mapMenu = new MapMenu();
        MapMenu.setParentMenu(this);
        mapMenu.setUsername("");
        mapMenu.start(stage);
    }

    public void exitGame(MouseEvent mouseEvent) {
        exit(stage);
    }

    private void exit(Stage stage) {
        DatabaseController databaseController = new DatabaseController();
        databaseController.exportUsers();
        System.exit(1);
    }

    public void instantGame(MouseEvent mouseEvent) throws IOException {
        GameController gameController = new GameController("", new GameBoard(), 3);
        GameMenu gameMenu = new GameMenu();
        gameController.setGameMenu(gameMenu);
        GameMenu.setGameController(gameController);
        GameMenu.setUsername("");
        GameMenu.setExitMenu(this);
        gameMenu.start(stage);
    }
}
