package view;

import controller.GameBoardObject;
import controller.GameController;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GameMenu extends Menu {

    private static Stage stage;
    private static Menu exitMenu;
    private static GameController gameController;
    private static String username;
    private static AnimationTimer animationTimer;
    private static boolean isPaused;

    public GridPane gameBoard = new GridPane();
    public Label pointsLabel;
    public Label usernameLabel;
    public GridPane lifeBoard;
    private int life;

    @Override
    public void start(Stage stage) throws IOException {
        Sound.stopMusic();

        GameMenu.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("GameMenu.fxml"));
        Scene scene = new Scene(root, 1080, 720);
        scene.getStylesheets().add(String.valueOf(getClass().getResource("style/style.css")));

        isPaused = true;
        scene.setOnKeyPressed(event -> {
            if (isPaused) {
                isPaused = false;
                animationTimer.start();
            }
            if (event.getCode() == KeyCode.UP) {
                setPacmanDirection("up");
            } else if (event.getCode() == KeyCode.DOWN) {
                setPacmanDirection("down");
            } else if (event.getCode() == KeyCode.RIGHT) {
                setPacmanDirection("right");
            } else if (event.getCode() == KeyCode.LEFT) {
                setPacmanDirection("left");
            } else if (event.getCode() == KeyCode.ESCAPE) {
                pauseGameMenu();
            }
        });

        stage.setScene(scene);
        stage.setTitle("Pacman");
        stage.show();
    }

    @FXML
    public void initialize() {
        updateGameBoard(gameController.getGameBoard());
        usernameLabel.setText(username);
        life = gameController.getLife();
        updateLife(life);
        animationTimer = new AnimationTimer() {
            public void handle(long currentNanoTime) {
                if (!gameController.isEnded()) {
                    try {
                        gameController.startGame();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                    updateGameBoard(gameController.getGameBoard());
                    updatePoints(gameController.getPoints());
                    if (gameController.getLife() != life) {
                        life = gameController.getLife();
                        updateLife(life);
                    }
                } else {
                    try {
                        animationTimer.stop();
                        endGame();
                    } catch (Exception ignored) {
                    }
                }
            }
        };
    }


    public static void setGameController(GameController gameController) {
        GameMenu.gameController = gameController;
    }

    public static void setUsername(String username) {
        GameMenu.username = username;
    }

    public static void setExitMenu(Menu exitMenu) {
        GameMenu.exitMenu = exitMenu;
    }

    public void pause() {
        isPaused = true;
        animationTimer.stop();
    }

    private void updateLife(int life) {
        lifeBoard.getChildren().clear();
        for (int i = 0; i < life; i++) lifeBoard.add(Map.getHeart(), i, 0);
    }

    public void updateGameBoard(GameBoardObject[][] gameBoard) {
        this.gameBoard.getChildren().clear();
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                this.gameBoard.add(Map.getMapShape(gameBoard[i][j]), j, i);
            }
        }
    }

    public void updatePoints(int points) {
        pointsLabel.setText(String.valueOf(points));
    }

    private void setPacmanDirection(String direction) {
        gameController.setPacmanDirection(direction);
    }

    private void saveAndExitGame() throws Exception {
        animationTimer = null;
        gameController.saveGame();
        exitMenu.start(stage);
    }

    private void pauseGameMenu() {
        isPaused = true;
        animationTimer.stop();
        boolean isExit = GetInput.getYesNoAnswer("Pause", "do you want to save game and exit to Main Menu?");
        if (isExit) {
            try {
                saveAndExitGame();
            } catch (Exception ignored) {
            }
        }
    }

    private void endGame() throws Exception {
        animationTimer = null;
        gameController.endGame();
        ShowOutput.showOutput("LOOOOOSER!!!", "You LOST with " + gameController.getPoints() + " points :((");
        exitMenu.start(stage);
    }

    public void eatPointSound() {
        Sound.playSound("eat_point");
    }

    public void eatEnergySound() {
        Sound.playSound("eat_energy");
    }

    public void eatGhostSound() {
        Sound.playSound("eat_ghost");
    }

    public void deathSound() {
        Sound.playSound("death");
    }
}
