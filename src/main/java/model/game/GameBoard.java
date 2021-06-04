package model.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameBoard {

    private final static int REMOVE_WALL_RATIO = 4;
    private final static int MAX_X = 37;
    private final static int MAX_Y = 25;

    private GamePiece[][] gamePieces;
    private int[][] maze;
    private Pacman pacman;
    private ArrayList<Ghost> ghosts;

    public GameBoard() {
        gamePieces = new GamePiece[MAX_Y][MAX_X];
        generateNewBoard();
    }

    public GameBoard(int[][] maze) {
        gamePieces = new GamePiece[MAX_Y][MAX_X];
        this.maze = maze;
        setGamePieces();
    }


    public void setGamePieces(GamePiece[][] gamePieces) {
        this.gamePieces = gamePieces;
    }

    public static int getMaxX() {
        return MAX_X;
    }

    public static int getMaxY() {
        return MAX_Y;
    }

    public GamePiece[][] getGamePieces() {
        return gamePieces;
    }

    public ArrayList<Ghost> getGhosts() {
        return ghosts;
    }

    public Pacman getPacman() {
        return pacman;
    }

    public int getTotalPaths() {
        int totalPaths = 0;
        for (int i = 1; i < gamePieces.length - 1; i++) {
            for (int j = 1; j < gamePieces[i].length - 1; j++) {
                if (gamePieces[i][j] instanceof Path) {
                    totalPaths++;
                }
            }
        }
        return totalPaths + 8;
    }

    public void generateNewBoard() {
        Maze mazeGenerator = new Maze(MAX_X, MAX_Y);
        int[][] maze = removeRandomWalls(mazeGenerator.generate());
        this.maze = maze;
        setGamePieces();
    }

    private int[][] removeRandomWalls(int[][] maze) {
        for (int i = 1; i < maze.length - 1; i++) {
            int wallsToRemove = getWallsCount(maze[i]) / REMOVE_WALL_RATIO;
            int removedWallsCount = 0;
            for (int j = 1; j < maze[i].length - 1; j++) {
                if (maze[i][j] == 1) {
                    if (removedWallsCount < wallsToRemove) {
                        if ((new Random()).nextInt(REMOVE_WALL_RATIO) == 0) {
                            maze[i][j] = 0;
                        }
                    } else {
                        break;
                    }
                }
            }
        }
        return maze;
    }

    private int getWallsCount(int[] row) {
        int count = 0;
        for (int i = 1; i < row.length - 1; i++) {
            if (row[i] == 1) count++;
        }
        return count;
    }

    public void setGamePieces() {
        for (int i = 0; i < MAX_Y; i++) {
            for (int j = 0; j < MAX_X; j++) {
                if (maze[i][j] == 1) {
                    gamePieces[i][j] = new Wall();
                } else {
                    gamePieces[i][j] = new Path();
                }
            }
        }
        // put pacman and ghosts:
        ghosts = new ArrayList<>();
        Ghost ghost1 = new Ghost(1);
        ghosts.add(ghost1);
        gamePieces[1][1] = ghost1;
        Ghost ghost2 = new Ghost(2);
        ghosts.add(ghost2);
        gamePieces[1][MAX_X - 2] = ghost2;
        Ghost ghost3 = new Ghost(3);
        ghosts.add(ghost3);
        gamePieces[MAX_Y - 2][1] = ghost3;
        Ghost ghost4 = new Ghost(4);
        ghosts.add(ghost4);
        gamePieces[MAX_Y - 2][MAX_X - 2] = ghost4;
        gamePieces[MAX_Y / 2][MAX_X / 2] = new Pacman();
        pacman = (Pacman) gamePieces[MAX_Y / 2][MAX_X / 2];
        // put energy:
        gamePieces[MAX_Y / 4][MAX_X / 4] = new Energy();
        gamePieces[MAX_Y / 4][MAX_X / 4 * 3] = new Energy();
        gamePieces[MAX_Y / 4 * 3][MAX_X / 4] = new Energy();
        gamePieces[MAX_Y / 4 * 3][MAX_X / 4 * 3] = new Energy();
    }

    @Override
    public GameBoard clone() {
        return new GameBoard(this.maze);
    }
}
