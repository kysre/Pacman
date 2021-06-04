package controller;

import model.User;
import model.game.*;
import view.GameMenu;

public class GameController {

    private final Game game;
    private final User user;
    private GameMenu gameMenu;


    public GameController(String username, GameBoard gameBoard, int life) {
        this.user = User.getUserByUsername(username);
        game = new Game(user, gameBoard, life, this);
    }

    public GameController(String username, Game game) {
        this.user = User.getUserByUsername(username);
        this.game = game;
    }


    public static boolean doesLastGameExist(String username) {
        User user = User.getUserByUsername(username);
        return user.doesLastGameExist();
    }

    public static GameMenu loadLastGame(String username) {
        User user = User.getUserByUsername(username);
        Game lastGame = user.getLastGame();
        GameController lastGameController = new GameController(username, lastGame);
        lastGame.setGameController(lastGameController);
        GameMenu gameMenu = new GameMenu();
        lastGameController.setGameMenu(gameMenu);
        GameMenu.setGameController(lastGameController);
        GameMenu.setUsername(username);
        return gameMenu;
    }

    public void setGameMenu(GameMenu gameMenu) {
        this.gameMenu = gameMenu;
    }

    public void setPacmanDirection(String direction) {
        switch (direction) {
            case "up":
                game.getGameBoard().getPacman().setDirection(MovementDirection.UP);
                break;

            case "down":
                game.getGameBoard().getPacman().setDirection(MovementDirection.DOWN);
                break;

            case "right":
                game.getGameBoard().getPacman().setDirection(MovementDirection.RIGHT);
                break;

            case "left":
                game.getGameBoard().getPacman().setDirection(MovementDirection.LEFT);
                break;
        }
    }

    public GameBoardObject[][] getGameBoard() {
        GamePiece[][] gamePieces = game.getGameBoard().getGamePieces();
        return BoardMaker.getGameBoard(gamePieces);
    }

    public int getLife() {
        return game.getLife();
    }

    public void startGame() throws Exception {
        game.startGame();
    }

    public int getPoints() {
        return game.getPoints();
    }

    public void pauseGame() {
        gameMenu.pause();
    }

    public boolean isEnded() {
        return game.isGameEnded();
    }

    public void saveGame() {
        game.saveGame();
    }

    public void endGame() {
        game.endGame();
    }

    public void playEatPointSound() {
        gameMenu.eatPointSound();
    }

    public void playEatEnergySound() {
        gameMenu.eatEnergySound();
    }

    public void playEatGhostSound() {
        gameMenu.eatGhostSound();
    }

    public void playDeathSound() {
        gameMenu.deathSound();
    }
}
