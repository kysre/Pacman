package model;

import controller.DatabaseController;
import controller.GameController;
import model.game.Game;
import model.game.GameBoard;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class User {

    private final static ArrayList<User> users;
    private final String username;
    private final ArrayList<GameBoard> userGameBoards;
    private String password;
    private int highScore;
    private LocalDateTime highScoreDateTime;
    private GameBoard lastGameBoard;
    private ArrayList<Integer> lastGameStats;

    static {
        DatabaseController databaseController = new DatabaseController();
        users = databaseController.importUsers();
    }


    public User(String username, String password) {
        this.username = username;
        this.password = password;
        highScore = 0;
        users.add(this);
        userGameBoards = new ArrayList<>();

        // init static gameBoards for every user
        DatabaseController databaseController = new DatabaseController();
        ArrayList<GameBoard> staticGameBoards = databaseController.getStaticGameBoards();
        for (GameBoard gameBoard : staticGameBoards) userGameBoards.add(gameBoard.clone());
    }


    public static User getUserByUsername(String username) {
        if (username.equals("")) return null;
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static ArrayList<User> getAllUsers() {
        return users;
    }

    public static boolean doesUserExist(String username) {
        return getUserByUsername(username) != null;
    }

    public static void deleteUser(String username) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                users.remove(i);
                break;
            }
        }
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHighScore(int score) {
        if (score > highScore) {
            highScore = score;
            highScoreDateTime = LocalDateTime.now();
        }
    }

    public void setLastGame(Game lastGame) {
        if (lastGame != null) {
            this.lastGameBoard = lastGame.getGameBoard();
            this.lastGameStats = lastGame.getStats();
        } else {
            lastGameBoard = null;
            lastGameStats = null;
        }
    }

    public void addGameBoard(GameBoard gameBoard) {
        userGameBoards.add(gameBoard);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getHighScore() {
        return highScore;
    }

    public LocalDateTime getHighScoreDateTime() {
        return highScoreDateTime;
    }

    public ArrayList<GameBoard> getUserGameBoards() {
        for (GameBoard gameBoard : userGameBoards) {
            gameBoard.setGamePieces();
        }
        return userGameBoards;
    }

    public boolean isPasswordCorrect(String password) {
        return this.password.equals(password);
    }

    public boolean doesLastGameExist() {
        return lastGameBoard != null;
    }

    public Game getLastGame() {
        return new Game(this, lastGameBoard, lastGameStats);
    }

    public boolean doesGameBoardExist(GameBoard gameBoard) {
        for (GameBoard userGameBoard : userGameBoards) {
            if (userGameBoard.equals(gameBoard)) return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        } else if (object instanceof User) {
            return ((User) object).getUsername().equals(this.username);
        }
        return false;
    }
}
