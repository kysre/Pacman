package view;

import controller.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenu extends Menu {
    private static Menu parentMenu;
    private static Stage stage;
    private static String username;
    public Button resumeGameButton;
    public Button startGameButton;
    public Button scoreboardButton;
    public Button profileButton;
    public Button logoutButton;

    @Override
    public void start(Stage stage) throws IOException {
        MainMenu.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene scene = new Scene(root, 1080, 720);
        scene.getStylesheets().add(String.valueOf(getClass().getResource("style/style.css")));
        stage.setScene(scene);
        stage.show();
    }

    public void setParentMenu(Menu parentMenu) {
        MainMenu.parentMenu = parentMenu;
    }

    public void setUsername(String username) {
        MainMenu.username = username;
    }


    public void resumeGame(MouseEvent mouseEvent) throws IOException {
        if (GameController.doesLastGameExist(username)) {
            GameMenu gameMenu = GameController.loadLastGame(username);
            GameMenu.setExitMenu(this);
            gameMenu.start(stage);
        } else {
            ShowOutput.showOutput("Error", "Last game doesn't exist! please start a new game.");
        }
    }

    public void startGame(MouseEvent mouseEvent) throws IOException {
        MapMenu mapMenu = new MapMenu();
        MapMenu.setParentMenu(this);
        mapMenu.setUsername(username);
        mapMenu.start(stage);
    }

    public void scoreboard(MouseEvent mouseEvent) throws IOException {
        ScoreboardMenu scoreboardMenu = new ScoreboardMenu();
        scoreboardMenu.setParentMenu(this);
        scoreboardMenu.start(stage);
    }

    public void profile(MouseEvent mouseEvent) throws IOException {
        ProfileMenu profileMenu = new ProfileMenu();
        profileMenu.setParentMenu(this);
        profileMenu.setExitMenu(parentMenu);
        profileMenu.setUsername(username);
        profileMenu.start(stage);
    }

    public void logout(MouseEvent mouseEvent) throws Exception {
        parentMenu.start(stage);
    }
}
