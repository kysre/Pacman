package view;

import controller.ScoreboardController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ScoreboardMenu extends Menu {
    private static Menu parentMenu;
    private static Stage stage;
    public GridPane scoreboard;

    @Override
    public void start(Stage stage) throws IOException {
        ScoreboardMenu.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("ScoreboardMenu.fxml"));
        Scene scene = new Scene(root, 1080, 720);
        scene.getStylesheets().add(String.valueOf(getClass().getResource("style/style.css")));
        stage.setScene(scene);
        stage.setTitle("Pacman");
        stage.show();
    }

    @FXML
    public void initialize() {
        showScoreboard();
        Label headerLabel = getCell("Rank");
        headerLabel.setStyle("-fx-font-family: Arial;" +
                "-fx-font-size: 14;" +
                "-fx-font-weight: bold;");
        scoreboard.add(headerLabel, 0, 0);
        Label usernameLabel = getCell("Username");
        usernameLabel.setStyle("-fx-font-family: Arial;" +
                "-fx-font-size: 14;" +
                "-fx-font-weight: bold;");
        scoreboard.add(usernameLabel, 1, 0);
        Label highScoreLabel = getCell("High Score");
        highScoreLabel.setStyle("-fx-font-family: Arial;" +
                "-fx-font-size: 14;" +
                "-fx-font-weight: bold;");
        scoreboard.add(highScoreLabel, 2, 0);
    }

    public void setParentMenu(Menu parentMenu) {
        ScoreboardMenu.parentMenu = parentMenu;
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        parentMenu.start(stage);
    }

    private void showScoreboard() {
        LinkedHashMap<String, Integer> scoreboardLinkedHashMap = ScoreboardController.getScoreboard();
        ArrayList<String> keySetList = new ArrayList<>();
        keySetList.addAll(scoreboardLinkedHashMap.keySet());

        int lastHighScore = -1;
        int lastRank = 0;
        for (int i = 0; i < keySetList.size(); i++) {
            if (i == 10) break;
            int rank = i + 1;
            String username = keySetList.get(i);
            int highScore = scoreboardLinkedHashMap.get(username);
            if (highScore == lastHighScore) {
                rank = lastRank;
            } else {
                lastRank = rank;
            }
            scoreboard.add(getCell(String.valueOf(rank)), 0, i + 1);
            scoreboard.add(getCell(username), 1, i + 1);
            scoreboard.add(getCell(String.valueOf(highScore)), 2, i + 1);

            lastHighScore = highScore;
        }
    }

    private Label getCell(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-family: Arial;" +
                "-fx-font-size: 13;" +
                "-fx-font-weight: bold;");
        label.setPrefWidth(100);
        label.setPrefHeight(26);
        label.setAlignment(Pos.CENTER);
        return label;
    }
}
