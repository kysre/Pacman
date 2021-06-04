package controller;

import model.User;
import model.game.GameBoard;
import model.game.GamePiece;
import view.GameMenu;
import view.MapMenu;

import java.util.ArrayList;

public class MapController {
    private final User user;
    private final ArrayList<GameBoard> gameBoards;
    private int mapIndex;

    public MapController(String username) {
        this.user = User.getUserByUsername(username);
        ArrayList<GameBoard> userGameBoards = user.getUserGameBoards();
        gameBoards = new ArrayList<>();
        gameBoards.addAll(userGameBoards);
        mapIndex = -1;
    }

    public void nextMap() {
        if (mapIndex < gameBoards.size() - 1) {
            mapIndex++;
            GamePiece[][] gamePieces = gameBoards.get(mapIndex).getGamePieces();
            MapMenu.setGameBoard(BoardMaker.getGameBoard(gamePieces));
        }
    }

    public void previousMap() {
        if (mapIndex > 0) {
            mapIndex--;
            GamePiece[][] gamePieces = gameBoards.get(mapIndex).getGamePieces();
            MapMenu.setGameBoard(BoardMaker.getGameBoard(gamePieces));
        }
    }

    public void generateMap() {
        GameBoard newGameBoard = new GameBoard();
        gameBoards.add(newGameBoard);
    }

    public void saveMap() {
        if (!user.doesGameBoardExist(gameBoards.get(mapIndex))) {
            user.addGameBoard(gameBoards.get(mapIndex));
        }
    }

    public GameMenu startGame(int life) {
        GameController gameController = new GameController(user.getUsername(), gameBoards.get(mapIndex), life);
        GameMenu gameMenu = new GameMenu();
        gameController.setGameMenu(gameMenu);
        GameMenu.setGameController(gameController);
        GameMenu.setUsername(user.getUsername());
        return gameMenu;
    }
}
