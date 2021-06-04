package model.game;

import controller.GameController;
import model.User;

import java.util.ArrayList;

public class Game {

    private static final double TIME_FRAME;
    private static final double START_WAIT_TIME;
    private static final double EAT_WAIT_TIME;
    private static final double SCARED_TIME;

    private final User user;
    private final GameBoard gameBoard;
    private final int totalPaths;
    private int paths;
    private int life;
    private int points;
    private Pacman pacman;
    private ArrayList<Ghost> ghosts;
    private GameController gameController;
    private int eatenGhosts;

    static {
        TIME_FRAME = 1.0 / 60.0;
        SCARED_TIME = 10.0;
        START_WAIT_TIME = 2.0;
        EAT_WAIT_TIME = 5.0;
    }


    public Game(User user, GameBoard gameBoard, int life, GameController gameController) {
        paths = 0;
        points = 0;
        gameBoard.setGamePieces();
        this.user = user;
        this.gameBoard = gameBoard;
        this.life = life;
        this.totalPaths = gameBoard.getTotalPaths();
        this.pacman = gameBoard.getPacman();
        this.ghosts = gameBoard.getGhosts();
        this.gameController = gameController;
    }

    public Game(User user, GameBoard gameBoard, ArrayList<Integer> stats) {
        this.paths = stats.get(0);
        this.life = stats.get(1);
        this.points = stats.get(2);

        this.user = user;
        this.gameBoard = gameBoard;
        this.totalPaths = gameBoard.getTotalPaths();
        this.pacman = gameBoard.getPacman();
        this.ghosts = gameBoard.getGhosts();
    }


    public static double getTimeFrame() {
        return TIME_FRAME;
    }

    public static double getStartWaitTime() {
        return START_WAIT_TIME;
    }

    public static double getEatWaitTime() {
        return EAT_WAIT_TIME;
    }

    public static double getScaredTime() {
        return SCARED_TIME;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public GameController getGameController() {
        return gameController;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public int getPoints() {
        return points;
    }

    public int getLife() {
        return life;
    }

    public ArrayList<Integer> getStats() {
        ArrayList<Integer> stats = new ArrayList<>();
        stats.add(paths);
        stats.add(life);
        stats.add(points);
        return stats;
    }

    public void startGame() throws Exception {
        if (!ghosts.get(0).isScared()) {
            eatenGhosts = 0;
        }

        pacman.nextMove(this);
        for (Ghost ghost : ghosts) {
            ghost.nextMove(this);
        }
        if (user != null) {
            user.setHighScore(points);
        }

        // check if all points are eaten by pacman
        if (paths == totalPaths) {
            resetGameBoard();
            pauseGame();
        }
    }

    public boolean isGameEnded() {
        return life < 1;
    }

    public boolean canEatGhost() {
        return ghosts.get(0).isScared();
    }

    public void setGhostsScared() {
        for (Ghost ghost : ghosts) {
            ghost.setScared();
        }
    }

    public void increasePoint(int point) {
        this.points += point;
    }

    public void increasePath() {
        paths++;
    }

    public void reduceLife() {
        life--;
    }

    public void increasePointsForEatingGhost() {
        eatenGhosts++;
        increasePoint(eatenGhosts * 200);
    }

    public void resetGameBoard() {
        gameBoard.setGamePieces();
        this.pacman = gameBoard.getPacman();
        this.ghosts = gameBoard.getGhosts();
    }

    public void pauseGame() {
        gameController.pauseGame();
    }

    public void saveGame() {
        if (user != null) {
            user.setLastGame(this);
        }
    }

    public void endGame() {
        if (user != null) {
            user.setLastGame(null);
        }
    }
}
